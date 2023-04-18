package com.example.demo.repository;

import com.example.demo.entity.ClientConnectionEntity;
import com.example.demo.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientConnectionRepository extends JpaRepository<ClientConnectionEntity, Long> {

    List<ClientConnectionEntity> getByParentClient(ClientEntity parentClient);
}
