package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    private final ClientConnectionRepository clientConnectionRepository;
    private final ClientRepository clientRepository;
    private final ClientSalesRepository clientSalesRepository;
    private final GenerationRepository generationRepository;
    private final GenerationSquadRepository generationSquadRepository;

    private final Environment environment;

    public CalculationService(ClientConnectionRepository clientConnectionRepository, ClientRepository clientRepository, ClientSalesRepository clientSalesRepository, GenerationRepository generationRepository, GenerationSquadRepository generationSquadRepository, Environment environment) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.clientRepository = clientRepository;
        this.clientSalesRepository = clientSalesRepository;
        this.generationRepository = generationRepository;
        this.generationSquadRepository = generationSquadRepository;
        this.environment = environment;
    }

    public String calculate() {
        OffsetDateTime dateEnd = calculateFirstDateOfMonth();
        OffsetDateTime dateStart = dateEnd.minusMonths(1);

        Map<Long, Double> sumByClientsId = clientRepository.getClientsAndSum(dateStart, dateEnd)
                .stream()
                .collect(Collectors.toMap(ClientRepository.ClientSum::getClientId, ClientRepository.ClientSum::getClientSum));

        List<ClientSalesEntity> clientSalesEntityList = fillClientSales(clientRepository.findAll(), sumByClientsId);
        Map<Long, ClientSalesEntity> clientSalesByClientId = clientSalesEntityList.stream().collect(Collectors.toMap(cls -> cls.getClient().getId(), cls -> cls));

        clientSalesEntityList.forEach(clientSales -> fillGenerationInfo(clientSales.getClient(), clientSalesByClientId, 1, clientSales));

        return "success";
    }

    private void fillGenerationInfo(ClientEntity client, Map<Long, ClientSalesEntity> clientSalesByClientId, Integer generationNumber, ClientSalesEntity rootClientSalesEntity) {
        System.gc();

        List<ClientEntity> clientChildList =    client.getClientConnectionChildList().stream()
                .map(ClientConnectionEntity::getChildClient)
                .collect(Collectors.toList());

        if(clientChildList.size() != 0) {

            Integer secondGeneration = generationNumber + 1;
            clientChildList.forEach(clientEntity -> fillGenerationInfo(clientEntity, clientSalesByClientId, secondGeneration, rootClientSalesEntity));

            ClientSalesEntity clientSalesEntity = clientSalesByClientId.get(client.getId());
            Double clientSum = clientSalesEntity.getPersonalSalesSum();
            Double clientPureSalesSum = clientSalesEntity.getPureSalesSum();


            Double allSumFirstGen = clientChildList.stream()
                    .map(child -> clientSalesByClientId.get(child.getId()))
                    .map(ClientSalesEntity::getPersonalSalesSum)
                    .reduce(Double::sum).get();
            Double finalSalesSum = clientSum + allSumFirstGen;

            System.out.println("second result" + finalSalesSum);

            Double allPureSumFirstGen = clientChildList.stream()
                    .map(child -> clientSalesByClientId.get(child.getId()))
                    .map(ClientSalesEntity::getPureSalesSum)
                    .reduce(Double::sum).get();
            Double finalPureSalesSum = clientPureSalesSum + allPureSumFirstGen;

            GenerationEntity generation = new GenerationEntity();
            generation.setClientSales(rootClientSalesEntity);
            generation.setGenerationLevel(generationNumber);
            generation.setMembersQty(clientChildList.size());
            generation.setSalesSum(finalSalesSum);
            generation.setPureSalesSum(finalPureSalesSum);

            GenerationEntity savedGeneration = generationRepository.save(generation);

            fillGenerationSquad(clientChildList, savedGeneration, clientSalesByClientId);
        }

    }

    private void fillGenerationSquad(List<ClientEntity> clientChildList, GenerationEntity generation, Map<Long, ClientSalesEntity> clientSalesByClientId) {
        clientChildList.forEach(childClient -> {
            GenerationSquadEntity generationSquadEntity = new GenerationSquadEntity();
            generationSquadEntity.setGenerationEntity(generation);
            generationSquadEntity.setClientEntity(childClient);
            generationSquadEntity.setClientType(childClient.getType().getName());
            generationSquadEntity.setRewardLevel(childClient.getLevel().getName());

            ClientSalesEntity clientSalesEntity = clientSalesByClientId.get(childClient.getId());
            generationSquadEntity.setPersonalSalesSum(clientSalesEntity.getPersonalSalesSum());
            generationSquadEntity.setPureSalesSum(clientSalesEntity.getPureSalesSum());

            generationSquadRepository.save(generationSquadEntity);

        });
    }

    private List<ClientSalesEntity> fillClientSales(List<ClientEntity> allClients, Map<Long, Double> sumByClientsId) {
        String company = calculateCompany();
        List<ClientSalesEntity> savedClientSalesList = new ArrayList<>();

        allClients.forEach(client -> {
            ClientSalesEntity clientSalesEntity = new ClientSalesEntity();
            Double fullSum = Optional.ofNullable(sumByClientsId.get(client.getId())).orElse(0.0);

            clientSalesEntity.setCompanyName(company);

            List<ClientSalesEntity> clientSalesByClientAndCompanyName = clientSalesRepository.findByClientAndCompanyName(client, company);
            Integer version = clientSalesByClientAndCompanyName.stream().map(ClientSalesEntity::getVersion).max(Integer::compareTo).orElse(0);
            clientSalesEntity.setVersion(version + 1);

            clientSalesEntity.setClient(client);
            clientSalesEntity.setClientFio(client.getFio());
            clientSalesEntity.setClientType(client.getType().getName());
            clientSalesEntity.setRewardLevel(client.getLevel().getName());
            clientSalesEntity.setPersonalSalesSum(fullSum);

            Double pureSum = fullSum != 0.0
                    ? fullSum - (fullSum / 100 * client.getDiscount()) - (fullSum / 100 * Integer.parseInt(environment.getProperty("NDS")))
                    : fullSum;
            clientSalesEntity.setPureSalesSum(pureSum);

            savedClientSalesList.add(clientSalesRepository.save(clientSalesEntity));
        });

        return savedClientSalesList;
    }


    private String calculateCompany() {
        LocalDate now = LocalDate.now();
        String month = Integer.toString(now.getMonthValue());
        String finalMonth = month.length() == 1 ? "0" + month : month;
        return now.getYear() + "" + finalMonth;
    }

    private OffsetDateTime calculateFirstDateOfMonth() {
        String templateForStartOfDay = "T00:00:00+03:00";
        OffsetDateTime now = OffsetDateTime.now();
        int dayOfMonth = now.getDayOfMonth();
        OffsetDateTime dateEnd = now.minusDays(dayOfMonth -1);
        String dateEndString = dateEnd.toString().substring(0, 10) + templateForStartOfDay;
        return OffsetDateTime.parse(dateEndString);
    }

}
