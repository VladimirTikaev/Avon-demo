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

    @ManyToOne
    @JoinColumn(name = "type_id")
    private ClientTypeEntity type;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private RemunerationLevelEntity level;

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

    @OneToMany(
            mappedBy = "parentClient",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<ClientConnectionEntity> clientConnectionChildList = new ArrayList();

    @OneToOne(mappedBy = "childClient")
    private ClientEntity parentClient;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientTypeEntity getType() {
        return type;
    }

    public void setType(ClientTypeEntity type) {
        this.type = type;
    }

    public RemunerationLevelEntity getLevel() {
        return level;
    }

    public void setLevel(RemunerationLevelEntity level) {
        this.level = level;
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

    public List<ClientConnectionEntity> getClientConnectionChildList() {
        return clientConnectionChildList;
    }

    public void setClientConnectionChildList(List<ClientConnectionEntity> clientConnectionChildList) {
        this.clientConnectionChildList = clientConnectionChildList;
    }

    public ClientEntity getParentClient() {
        return parentClient;
    }

    public void setParentClient(ClientEntity parentClient) {
        this.parentClient = parentClient;
    }
}
