package org.dci.bookhaven.dto.cart;

import jakarta.persistence.*;
import lombok.*;
import org.dci.bookhaven.model.Coupon;

import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {
    private Long id;

    private Set<LineItemDto> lineItemDtos;

    private Coupon coupon;

}
