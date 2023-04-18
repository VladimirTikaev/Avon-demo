package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "generation")
public class GenerationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "generation_level")
    private Integer generationLevel;

    @Column(name = "members_qty")
    private Integer membersQty;

    @Column(name = "sales_sum")
    private Double salesSum;

    @Column(name = "pure_sales_sum")
    private Double pureSalesSum;

    @ManyToOne
    @JoinColumn(name = "client_sales_id")
    private ClientSalesEntity clientSales;

    @OneToMany(
            mappedBy = "generationEntity",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<GenerationSquadEntity> generationSquadList = new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGenerationLevel() {
        return generationLevel;
    }

    public void setGenerationLevel(Integer generationLevel) {
        this.generationLevel = generationLevel;
    }

    public Integer getMembersQty() {
        return membersQty;
    }

    public void setMembersQty(Integer membersQty) {
        this.membersQty = membersQty;
    }

    public Double getSalesSum() {
        return salesSum;
    }

    public void setSalesSum(Double salesSum) {
        this.salesSum = salesSum;
    }

    public Double getPureSalesSum() {
        return pureSalesSum;
    }

    public void setPureSalesSum(Double pureSalesSum) {
        this.pureSalesSum = pureSalesSum;
    }

    public ClientSalesEntity getClientSales() {
        return clientSales;
    }

    public void setClientSales(ClientSalesEntity clientSales) {
        this.clientSales = clientSales;
    }

    public List<GenerationSquadEntity> getGenerationSquadList() {
        return generationSquadList;
    }

    public void setGenerationSquadList(List<GenerationSquadEntity> generationSquadList) {
        this.generationSquadList = generationSquadList;
    }
}
