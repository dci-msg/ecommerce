package org.dci.bookhaven.dto;

import jakarta.persistence.*;
import lombok.*;
import org.dci.bookhaven.model.Coupon;
import org.dci.bookhaven.model.LineItem;
import org.dci.bookhaven.model.Shipping;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
    private Set<LineItem> lineItems;

    @OneToOne(cascade = CascadeType.ALL)
    private Coupon coupon;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private double total;

    @OneToOne(cascade = CascadeType.ALL)
    private Shipping shipping;

}
