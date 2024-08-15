package org.muyangj.bookhaven.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
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
    @DecimalMin(value = "0.0", message = "Price must be non-negative")
    private BigDecimal price;

    @NonNull
    @Column(nullable = false, unique = true)
    private String isbn;

    @Getter
    @NonNull
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate publicationDate;

    @NonNull
    @Column(nullable = false)
    @Min(value = 1, message = "The number of pages must be positive")
    private Integer pages;

    @NonNull
    @Column(nullable = false)
    private String language; // TODO change to enum

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private String imagePath;

    @NonNull
    @Column(nullable = false)
    @Min(value = 0, message = "The number of pages must be positive")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Book(@NonNull String author, @NonNull String title, @NonNull BigDecimal price, @NonNull String isbn,
                @NonNull LocalDate publicationDate, @NonNull Integer pages, @NonNull String language,
                @NonNull Integer quantity, @NonNull String imagePath, @NonNull String description, Category category) {
        this.author = author;
        this.title = title;
        this.price = price;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.pages = pages;
        this.language = language;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.imagePath = imagePath;
    }

    public void setPublicationDate(@NonNull LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
