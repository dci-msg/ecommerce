package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;


import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Long authorId;

    @NonNull
    private String title;

    @NonNull
    private BigDecimal price;

    @NonNull
    private String isbn;

    @NonNull
    private LocalDate publicationDate;

    @NonNull
    private Integer pages;

    @NonNull
    private String language; // TODO change to enum

    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
