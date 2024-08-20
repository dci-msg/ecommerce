package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    private ShoppingCart shoppingCart;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private Double total;

    @Column(name = "status", nullable = false)
    private String status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
