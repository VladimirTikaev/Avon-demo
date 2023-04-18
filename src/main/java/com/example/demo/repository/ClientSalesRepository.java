package com.example.demo.repository;

import com.example.demo.entity.ClientEntity;
import com.example.demo.entity.ClientSalesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientSalesRepository extends JpaRepository<ClientSalesEntity, Long> {

    List<ClientSalesEntity> findByClientAndCompanyName(ClientEntity clientEntity, String companyName);


}
