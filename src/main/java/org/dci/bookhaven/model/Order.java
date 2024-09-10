package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
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

    @ManyToOne
    private User user;

    @OneToMany
    private List<LineItem> lineItems;

    @Column(name = "coupon_code", nullable = true)
    private String couponCode;

    @OneToOne(cascade = CascadeType.MERGE)
    private Shipping shipping;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private BigDecimal total;

    @Column(name = "currency", nullable = false)
    private String currency;

    @ManyToOne
    private Address billingAddress;

    @Column(name = "status", nullable = false)
    private String status; // OPEN, CLOSED

    @CreationTimestamp
    private LocalDateTime createdAt;
}
