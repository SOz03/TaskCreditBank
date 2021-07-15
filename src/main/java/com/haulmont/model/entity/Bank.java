package com.haulmont.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "bank")
public class Bank {

    @Id
    @Column(name = "id_bank")
    private String idBank;

    @Column(name = "name_bank")
    private String nameBank;

    @OneToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY, mappedBy = "mainBank")
    private List<Client> clients;

    public Bank() {

    }

    public Bank(String nameBank, List<Client> clients) {
        this.idBank = UUID.randomUUID().toString();
        this.nameBank = nameBank;
        this.clients = clients;
    }

    public String getIdBank() {
        return idBank;
    }

    public void setIdBank(String idBank) {
        this.idBank = idBank;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public String getNameBank() {
        return nameBank;
    }

    public void setNameBank(String nameBank) {
        this.nameBank = nameBank;
    }

    public List<Client> getClients() {
        return clients;
    }


    @Override
    public String toString() {
        return "Bank{" +
                "idBank='" + idBank + '\'' +
                ", nameBank='" + nameBank + '\'' +
                ", clients=" + clients +
                '}';
    }
}
