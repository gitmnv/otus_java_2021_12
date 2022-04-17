package ru.otus.crm.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "street")
    private String street;

    public Address(String street) {
        this.id = null;
        this.street = street;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                '}';
    }

    public Address() {
    }

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }


    public Long getId() {
        return this.id;
    }

    public String getStreet() {
        return this.street;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStreet(String street) {
        this.street = street;
    }


}