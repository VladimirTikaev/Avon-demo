package com.example.demo.service;

import com.example.demo.entity.ClientConnectionEntity;
import com.example.demo.entity.ClientEntity;
import com.example.demo.repository.ClientConnectionRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    private final ClientConnectionRepository clientConnectionRepository;
    private final ClientRepository clientRepository;

    public CalculationService(ClientConnectionRepository clientConnectionRepository, ClientRepository clientRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.clientRepository = clientRepository;
    }

    public String calculate() {

        List<ClientRepository.ClientSum> clientSums = clientRepository.getClientsAndSum();

        clientRepository.getClientsAndSum().forEach(clientSum -> System.out.println("clientId = " + clientSum.getClientId() + " summ = " + clientSum.getClientSumma()));
        List<ClientEntity> allClients = clientRepository.findAll();
        Map<Long, ClientEntity> collect = allClients.stream().collect(Collectors.toMap(ClientEntity::getId, clientEntity -> clientEntity));

        LocalDate now = LocalDate.now();
        int monthValue = now.getMonthValue();


        String month = Integer.toString(now.getMonthValue());
        String finalMonth = month.length() == 1 ? "0" + month : month;
        System.out.println(now.getYear() + "" + finalMonth);


        System.out.println();


        return "success";
    }
}
