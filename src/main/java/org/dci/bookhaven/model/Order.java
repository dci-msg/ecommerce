package org.dci.bookhaven.model;

import jakarta.persistence.*;
import lombok.*;
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

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<LineItem> lineItems = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    private Coupon coupon;

    @Column(name = "total", nullable = false, precision = 2, columnDefinition = "double precision")
    private Double total;

    @Column(name = "currency", nullable = false)
    private String currency;

    @OneToOne(cascade = CascadeType.ALL)
    private Address billingAddress;

    @Column(name = "status", nullable = false)
    private String status; // OPEN, CLOSED

    @CreationTimestamp
    private LocalDateTime createdAt;
}
