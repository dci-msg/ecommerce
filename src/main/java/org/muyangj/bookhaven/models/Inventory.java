package org.muyangj.bookhaven.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "inventories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, unique = true)
    private Book book;

    @NonNull
    @Column(nullable = false)
    @Min(value = 0, message = "Stock must be non-negative")
    private Integer stock;

    public Inventory(Book book, @NonNull Integer stock) {
        this.book = book;
        this.stock = stock;
    }
}
