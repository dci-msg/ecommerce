package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long addressId;

    @Getter
    @ManyToOne
    @JoinColumn(name = "profileId", nullable = false)
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

    public Address(Long addressId, UserProfile userProfile, String street, String city, String zipCode, String country) {
        this.addressId = addressId;
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
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
