package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "profile_id", nullable = false)
    private UserProfile userProfile;

    @Getter
    @Column(nullable = true)
    private String street;
    @Getter
    @Column(nullable = true)
    private String city;
    @Getter
    @Column(nullable = true)
    private String zipCode;
    @Getter
    @Column(nullable = true)
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

    public Address(Long id, UserProfile userProfile, String street, String city, String zipCode, String country) {
        this.id = id;
        this.userProfile = userProfile;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", userProfile id=" + userProfile.getId()+
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public void setCountry(String country) {
        this.country = country;

    }
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
}
