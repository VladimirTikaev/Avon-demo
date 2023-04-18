package com.example.demo.service;

import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.ClientSalesEntity;
import com.example.demo.repository.ClientConnectionRepository;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ClientSalesRepository;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private final Environment environment;

    public CalculationService(ClientConnectionRepository clientConnectionRepository, ClientRepository clientRepository, ClientSalesRepository clientSalesRepository, Environment environment) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.clientRepository = clientRepository;
        this.clientSalesRepository = clientSalesRepository;
        this.environment = environment;
    }

    public String calculate() {
        Map<Long, Double> sumByClientsId = clientRepository.getClientsAndSum()
                .stream()
                .collect(Collectors.toMap(ClientRepository.ClientSum::getClientId, ClientRepository.ClientSum::getClientSum));

        List<ClientSalesEntity> clientSalesEntityList = fillClientSales(clientRepository.findAll(), sumByClientsId);
        Map<Long, ClientSalesEntity> clientSalesByClientId = clientSalesEntityList.stream().collect(Collectors.toMap(cls -> cls.getClient().getId(), cls -> cls));

        ClientEntity client = clientSalesByClientId.get(1L).getClient();

        calc(client, clientSalesByClientId, 1);

//
//        clientRepository.getClientsAndSum().forEach(clientSum -> System.out.println("clientId = " + clientSum.getClientId() + " summ = " + clientSum.getClientSum()));
//
//
//        List<ClientEntity> allClients = clientRepository.findAll();
//        Map<Long, ClientEntity> clientsById = allClients.stream().collect(Collectors.toMap(ClientEntity::getId, clientEntity -> clientEntity));


        return "success";
    }

    private void calc(ClientEntity clientEntity, Map<Long, ClientSalesEntity> clientSalesByClientId, Integer generation) {

        Double clientSum = clientSalesByClientId.get(clientEntity.getId()).getPersonalSalesSum();
        Double allSumFirstGen = clientEntity.getClientConnectionChildList().stream().map(clientConnection -> clientConnection.getChildClient())
                .map(child -> clientSalesByClientId.get(child.getId()))
                .map(cl -> cl.getPersonalSalesSum())
                .filter(sum -> sum != null).reduce((left, right) -> left + right).get();
        Double finalSum = clientSum + allSumFirstGen;


        System.out.println("second result" + finalSum);

    }

    private List<ClientSalesEntity> fillClientSales(List<ClientEntity> allClients, Map<Long, Double> sumByClientsId) {
        String company = calculateCompany();
        List<ClientSalesEntity> savedClientSalesList = new ArrayList<>();

        allClients.stream().forEach(client -> {
            ClientSalesEntity clientSalesEntity = new ClientSalesEntity();
            Double fullSum = Optional.ofNullable(sumByClientsId.get(client.getId())).orElse(0.0);



            clientSalesEntity.setCompanyName(company);

            List<ClientSalesEntity> clientSalesByClientAndCompanyName = clientSalesRepository.findByClientAndCompanyName(client, company);

            Integer version = clientSalesByClientAndCompanyName.stream().map(cs -> cs.getVersion()).max(Integer::compareTo).orElse(0);

            clientSalesEntity.setVersion(version + 1);

            clientSalesEntity.setClient(client);
            clientSalesEntity.setClientFio(client.getFio());
            clientSalesEntity.setClientType(client.getType().getName());
            clientSalesEntity.setRewardLevel(client.getLevel().getName());
            clientSalesEntity.setPersonalSalesSum(fullSum);

            Double pureSum = fullSum == 0.0
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


}
