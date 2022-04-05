package ru.otus.crm.model;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    @Column(name = "street")
    private String street;

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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Address)) return false;
        final Address other = (Address) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$street = this.getStreet();
        final Object other$street = other.getStreet();
        if (this$street == null ? other$street != null : !this$street.equals(other$street)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Address;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $street = this.getStreet();
        result = result * PRIME + ($street == null ? 43 : $street.hashCode());
        return result;
    }
}
