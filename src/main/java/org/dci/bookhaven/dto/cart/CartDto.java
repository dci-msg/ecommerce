package org.dci.bookhaven.dto.cart;

import jakarta.persistence.*;
import lombok.*;
import org.dci.bookhaven.model.Coupon;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartDto {
    private Long id;

    private List<LineItemDto> lineItemDtos;

    private Coupon coupon;

}
