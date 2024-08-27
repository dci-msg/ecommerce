package org.dci.bookhaven.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    //constructors
    public UserType() {
    }

    public UserType(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    //getters setters
    public Long getUserTypeId() {
        return id;
    }

    public void setUserTypeId(Long userTypeId) {
        this.id = userTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String userTypeName) {
        this.name = userTypeName;
    }


    //toString
    @Override
    public String toString() {
        return "UserType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
