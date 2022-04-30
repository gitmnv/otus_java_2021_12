package ru.otus.crm.model;

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

    @ManyToOne(fetch = FetchType.LAZY)
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


    public String toString() {
        return "Phone(id=" + this.getId() +
                ", number=" + this.getNumber() +")";
    }
}
