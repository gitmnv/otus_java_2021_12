package ru.otus.crm.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "phone")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone() {
    }

    public Phone(Long o, String s) {
        this.id = o;
        this.number = s;
    }

    public Phone(String s) {
        this.id = null;
        this.number = s;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\''
                + client +
                '}';
    }

    public Long getId() {
        return this.id;
    }

    public String getNumber() {
        return this.number;
    }

    public Client getClient() {
        return this.client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClient(Client client) {
        this.client = client;
    }



}
