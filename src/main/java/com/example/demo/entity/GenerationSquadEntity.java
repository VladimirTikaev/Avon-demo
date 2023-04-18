package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "generation_squad")
public class GenerationSquadEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_id")
    private Long clientId;

    @Column(name = "client_type")
    private String clientType;

    @Column(name = "reward_level")
    private String rewardLevel;

    @Column(name = "personal_sales_sum")
    private Double personalSalesSum;

    @Column(name = "pure_sales_sum")
    private Double pureSalesSum;

    @ManyToOne
    @JoinColumn(name = "generation_id")
    private GenerationEntity generationEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public GenerationEntity getGenerationEntity() {
        return generationEntity;
    }

    public void setGenerationEntity(GenerationEntity generationEntity) {
        this.generationEntity = generationEntity;
    }
}

