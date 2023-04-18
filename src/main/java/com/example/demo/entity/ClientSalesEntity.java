package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "client_sales")
public class ClientSalesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name")
    private String companyName;

    private Integer version;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @Column(name = "client_fio")
    private String clientFio;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "reward_level")
    private String rewardLevel;

    @Column(name = "personal_sales_sum")
    private Double personalSalesSum;

    @Column(name = "pure_sales_sum")
    private Double pureSalesSum;

    @OneToMany(
            mappedBy = "clientSales",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<GenerationEntity> generationList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public String getClientFio() {
        return clientFio;
    }

    public void setClientFio(String clientFio) {
        this.clientFio = clientFio;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getRewardLevel() {
        return rewardLevel;
    }

    public void setRewardLevel(String rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public Double getPersonalSalesSum() {
        return personalSalesSum;
    }

    public void setPersonalSalesSum(Double personalSalesSum) {
        this.personalSalesSum = personalSalesSum;
    }

    public Double getPureSalesSum() {
        return pureSalesSum;
    }

    public void setPureSalesSum(Double pureSalesSum) {
        this.pureSalesSum = pureSalesSum;
    }

    public List<GenerationEntity> getGenerationList() {
        return generationList;
    }

    public void setGenerationList(List<GenerationEntity> generationList) {
        this.generationList = generationList;
    }
}

