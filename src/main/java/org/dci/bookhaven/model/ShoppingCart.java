package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private List<LineItem> lineItems;

    @OneToOne(cascade = CascadeType.ALL)
    private Coupon coupon;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private double total;

    @ManyToOne
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Shipping shipping;

    @Column(name = "status", nullable = false)
    private String status;
}
