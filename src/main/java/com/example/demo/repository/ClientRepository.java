package com.example.demo.repository;

import com.example.demo.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    //todo добавить дату...т.к считаем по одному месяцу
    @Query(value = "select c.id as clientId, sum(p.product_count * p.price) as clientSum from avon.client c join avon.client_order co on c.id = co.client_id" +
            " join avon.product p on co.id = p.order_id  where co.status_id=3" +
            " group by c.id", nativeQuery = true)
    public List<ClientSum> getClientsAndSum();

    public interface ClientSum{
        Long getClientId();
        Double getClientSum();
    }
}
