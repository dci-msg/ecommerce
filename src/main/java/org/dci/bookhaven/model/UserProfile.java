package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "user_profiles")
public class UserProfile {
    //Getters and setters
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Getter
    @OneToMany(mappedBy = "userProfile")
    private List<Address> addresses;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "User_id", nullable = false)
    private User user;

    @Getter
    @Column(nullable = true)
    private String firstName;

    @Getter
    @Column(nullable = true)
    private String lastName;

    @Getter
    @Column(nullable = true)
    private LocalDate dateOfBirth;

    @Getter
    @Column(nullable = true)
    private String gender;


    //Constructors

    public UserProfile() {
    }

    public UserProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender, List<Address> addresses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addresses = addresses;
    }

    public UserProfile(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public UserProfile(String firstName, String lastName, LocalDate dateOfBirth, String gender, List<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.addresses = addresses;
    }



    public void setId(Long profileId) {
        this.id = profileId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
}

