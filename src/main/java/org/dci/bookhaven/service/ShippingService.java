package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Shipping;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface ShippingService {
    @Modifying
    @Transactional
    void create(Shipping shipping);

    List<Shipping> getAllShippings();

    Shipping findById(long id);
}
