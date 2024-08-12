package org.muyangj.bookhaven.models;

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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String author;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private BigDecimal price;

    @NonNull
    @Column(nullable = false, unique = true)
    private String isbn;

    @NonNull
    @Column(nullable = false)
    private LocalDate publicationDate;

    @NonNull
    @Column(nullable = false)
    private Integer pages;

    @NonNull
    @Column(nullable = false)
    private String language; // TODO change to enum

    @NonNull
    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = true)
    private Category category;
}
