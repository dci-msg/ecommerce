package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
