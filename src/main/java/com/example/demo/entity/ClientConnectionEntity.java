package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Table(name = "client_connection")
public class ClientConnectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private ClientEntity parentClient;

    @OneToOne
    @JoinColumn(name = "child_id")
    private ClientEntity childClient;

    @Column(name = "date_create")
    private OffsetDateTime creationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getParentClient() {
        return parentClient;
    }

    public void setParentClient(ClientEntity parentClient) {
        this.parentClient = parentClient;
    }

    public ClientEntity getChildClient() {
        return childClient;
    }

    public void setChildClient(ClientEntity childClient) {
        this.childClient = childClient;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
