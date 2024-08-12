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
        @Column(unique = true, nullable = false)
        private String name;

        @NonNull
        @Column(nullable = false)
        private String description;

        @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
        private List<Book> books;
}
