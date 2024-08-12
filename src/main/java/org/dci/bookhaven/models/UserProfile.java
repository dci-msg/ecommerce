package org.dci.bookhaven.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long ProfileID;

/*    @OneToOne
    @JoinColumn(name = "UserID", nullable = false)
    private User user;*/

    @Column(nullable = false)
    private String FirstName;

    @Column(nullable = false)
    private String LastName;

    @Column(nullable = true)
    private String DateOfBirth;

    @Column(nullable = true)
    private String Gender;

    @OneToMany(mappedBy = "userProfile")
    private List<Address> addresses;

    //Constructors
    public UserProfile() {
    }

    public UserProfile(Long profileID, String firstName, String lastName, String dateOfBirth, String gender, List<Address> addresses) {
        ProfileID = profileID;
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        this.addresses = addresses;
    }

    public UserProfile(String firstName, String lastName, String dateOfBirth, String gender, List<Address> addresses) {
        FirstName = firstName;
        LastName = lastName;
        DateOfBirth = dateOfBirth;
        Gender = gender;
        this.addresses = addresses;
    }

    //Getters and setters
    public Long getProfileID() {
        return ProfileID;
    }

    public void setProfileID(Long profileID) {
        ProfileID = profileID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
}

