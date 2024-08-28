package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users_type")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userTypeId")
    private Long id;

    private String userTypeName;

    @OneToMany(mappedBy = "userType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude // to break circular reference mistake = without List<User> users toString method
    private List<User> users;


}
