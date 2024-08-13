package org.dci.bookhaven.models;

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
    private Long ProfileID;

    @Getter
    @OneToMany(mappedBy = "userProfile")
    private List<Address> addresses;

/*  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "User_id", nullable = false)
    private User user;*/

    @Getter
    @Column(nullable = false)
    private String FirstName;

    @Getter
    @Column(nullable = false)
    private String LastName;

    @Column(nullable = true)
    private LocalDate DateOfBirth;

    @Getter
    @Column(nullable = true)
    private String Gender;


    //Constructors
    public UserProfile() {
    }

    public UserProfile(Long profileID, String firstName, String lastName, LocalDate dateOfBirth, String gender, List<Address> addresses) {
        ProfileID = profileID;
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        this.addresses = addresses;
    }

    public UserProfile(String firstName, String lastName, LocalDate dateOfBirth) {
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
    }

    public UserProfile(String firstName, String lastName, LocalDate dateOfBirth, String gender, List<Address> addresses) {
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        this.addresses = addresses;
    }

    public void setProfileID(Long profileID) {
        ProfileID = profileID;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}

