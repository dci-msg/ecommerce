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
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stripeId;

    @Column(name = "status", nullable = false)
    private String status; // SUCCESS, FAILED, PENDING

    @Column(name = "amount", nullable = false, precision = 2, columnDefinition = "double precision")
    private Double amount;

    @Column(name = "payment_method", nullable = false)
    private String paymentMethod; // card, paypal, etc

    @Column(name = "currency", nullable = false)
    private String currency;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
}
