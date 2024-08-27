package org.dci.bookhaven.dto.cart;

import jakarta.persistence.*;
import lombok.*;
import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Order;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LineItemDto {
    private Long id;

    private Book book;

    private int quantity;
}
