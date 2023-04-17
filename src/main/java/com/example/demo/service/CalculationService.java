package com.example.demo.service;

import com.example.demo.entity.ClientConnectionEntity;
import com.example.demo.entity.ClientEntity;
import com.example.demo.repository.ClientConnectionRepository;
import com.example.demo.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CalculationService {

    private final ClientConnectionRepository clientConnectionRepository;
    private final ClientRepository clientRepository;

    public CalculationService(ClientConnectionRepository clientConnectionRepository, ClientRepository clientRepository) {
        this.clientConnectionRepository = clientConnectionRepository;
        this.clientRepository = clientRepository;
    }

    public String calculate() {


        ClientConnectionEntity clientConnectionEntity = clientConnectionRepository.findById(1L).get();
        ClientConnectionEntity clientConnectionEntity2 = clientConnectionRepository.findById(4L).get();

        List<ClientRepository.ClientSum> clientSums = clientRepository.getClientsAndSum();

        clientRepository.getClientsAndSum().forEach(clientSum -> System.out.println("clientId = " + clientSum.getClientId() + " summ = " + clientSum.getClientSumma()));


        ClientEntity clientEntity = clientRepository.findById(2L).get();

        System.out.println();


        return "success";
    }
}
