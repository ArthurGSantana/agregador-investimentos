package tech.arthur.agregadorinvestimentos.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "billing_address")
public class BillingAddress {
    @Id
    @Column(name = "account_id")
    private String id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "street")
    private String street;

    @Column(name = "number")
    private Integer number;

    public BillingAddress(String id, String street, Integer number) {
        this.id = id;
        this.street = street;
        this.number = number;
    }

    public BillingAddress() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
