package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
