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
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<LineItem> lineItems;

    @Column(nullable = false)
    private boolean isOpen;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;
}
