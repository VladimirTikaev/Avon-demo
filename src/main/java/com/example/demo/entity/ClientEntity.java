package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class ClientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_id")
    private Long typeId;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "fio")
    private String fio;

    @Column(name = "discount")
    private Float discount;

    @OneToMany(
            mappedBy = "client",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<ClientOrderEntity> orderList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public List<ClientOrderEntity> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ClientOrderEntity> orderList) {
        this.orderList = orderList;
    }
}
