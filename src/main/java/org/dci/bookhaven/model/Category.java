package org.dci.bookhaven.model;

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
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NonNull
        @Column(unique = true, nullable = false)
        private String name;

        @NonNull
        @Column(nullable = false)
        private String description;

        @OneToMany(mappedBy = "category", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = false)
        private List<Book> books;

        public Category(@NonNull String name, @NonNull String description) {
                this.name = name;
                this.description = description;
        }

        public Category(Long id, @NonNull String name, @NonNull String description) {
                this.id = id;
                this.name = name;
                this.description = description;
        }
}
