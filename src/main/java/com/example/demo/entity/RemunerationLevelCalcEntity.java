package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "remuneration_level_calc")
public class RemunerationLevelCalcEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_sales")
    private Long totalSales;

    @Column(name = "remuneration_level")
    private String remunerationLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public String getRemunerationLevel() {
        return remunerationLevel;
    }

    public void setRemunerationLevel(String remunerationLevel) {
        this.remunerationLevel = remunerationLevel;
    }
}
