package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "line_items")
public class LineItem {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "line_total", nullable = false, precision = 2, columnDefinition = "double precision")
    private double lineTotal;

    @ManyToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;
}
