package com.example.demo.entity;

import com.example.demo.constant.RemunerationLevel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "remuneration_level")
public class RemunerationLevelEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private RemunerationLevel name;

    @Column(name = "extra_bonus")
    private Double extraBonus;

    @OneToMany(
            mappedBy = "level",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.ALL}
    )
    private List<ClientEntity> clientList= new ArrayList();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RemunerationLevel getName() {
        return name;
    }

    public void setName(RemunerationLevel name) {
        this.name = name;
    }

    public Double getExtraBonus() {
        return extraBonus;
    }

    public void setExtraBonus(Double extraBonus) {
        this.extraBonus = extraBonus;
    }

    public List<ClientEntity> getClientList() {
        return clientList;
    }

    public void setClientList(List<ClientEntity> clientList) {
        this.clientList = clientList;
    }
}
