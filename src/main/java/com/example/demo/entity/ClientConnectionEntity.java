package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime creationDate;

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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    
}
