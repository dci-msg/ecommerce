package org.muyangj.bookhaven.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long id;

        @NonNull
        @Column(unique = true)
        private String name;

        @NonNull
        private String description;

        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
        private List<Book> books;
}
