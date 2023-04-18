package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sales_percent")
public class SalesPercentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_sales")
    private Long totalSales;

    @Column(name = "first_gen_percent")
    private Double firstGenPercent;

    @Column(name = "second_gen_percent")
    private Double secondGenPercent;

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

    public Double getFirstGenPercent() {
        return firstGenPercent;
    }

    public void setFirstGenPercent(Double firstGenPercent) {
        this.firstGenPercent = firstGenPercent;
    }

    public Double getSecondGenPercent() {
        return secondGenPercent;
    }

    public void setSecondGenPercent(Double secondGenPercent) {
        this.secondGenPercent = secondGenPercent;
    }
}
