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
@Table(name = "shippings")
public class Shipping {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @OneToOne(mappedBy = "shipping", cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne
    private Address address;

    @Column(name = "shipping_method", nullable = false)
    private String shippingMethod;

    @Column(name = "shipping_cost", nullable = false, precision = 2, columnDefinition = "double precision")
    private double shippingCost;
}
