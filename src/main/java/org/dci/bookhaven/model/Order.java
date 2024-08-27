package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;
import org.dci.bookhaven.dto.cart.LineItemDto;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToOne
    private User user;

    @OneToMany
    private Set<LineItem> lineItems = new HashSet<>();

    @ManyToOne
    private Coupon coupon;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private Double total;

    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne
    private Address billingAddress;

    @Column(name = "status", nullable = false)
    private String status; // OPEN, CLOSED

    @CreationTimestamp
    private LocalDateTime createdAt;
}
