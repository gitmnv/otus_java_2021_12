package ru.otus.crm.model;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Transient
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone() {
    }

    public Phone(Long o, String s) {
        this.id = o;
        this.number = s;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", number='" + number + '\'' +
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Phone)) return false;
        final Phone other = (Phone) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$number = this.getNumber();
        final Object other$number = other.getNumber();
        if (this$number == null ? other$number != null : !this$number.equals(other$number)) return false;
        final Object this$client = this.getClient();
        final Object other$client = other.getClient();
        if (this$client == null ? other$client != null : !this$client.equals(other$client)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Phone;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $number = this.getNumber();
        result = result * PRIME + ($number == null ? 43 : $number.hashCode());
        final Object $client = this.getClient();
        result = result * PRIME + ($client == null ? 43 : $client.hashCode());
        return result;
    }
}
