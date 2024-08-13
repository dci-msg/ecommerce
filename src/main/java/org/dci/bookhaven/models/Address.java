package org.dci.bookhaven.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long AddressID;

    @ManyToOne
    @JoinColumn(name = "ProfileID", nullable = false)
    private UserProfile userProfile;

    @Getter
    @Column(nullable = false)
    private String street;
    @Getter
    @Column(nullable = false)
    private String city;
    @Getter
    @Column(nullable = false)
    private String zipCode;
    @Getter
    @Column(nullable = false)
    private String country;

    //Constructors

    public Address() {

    }
    public Address(String street, String city, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public Address(Long addressID, UserProfile userProfile, String street, String city, String zipCode, String country) {
        AddressID = addressID;
        this.userProfile = userProfile;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;

    }

}
