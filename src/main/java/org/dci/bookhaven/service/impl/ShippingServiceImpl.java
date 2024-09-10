package org.dci.bookhaven.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Shipping;
import org.dci.bookhaven.repository.ShippingRepository;
import org.dci.bookhaven.service.ShippingService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    public ShippingServiceImpl(ShippingRepository shippingRepository) {
        this.shippingRepository = shippingRepository;
    }

    @Modifying
    @Transactional
    @Override
    public void create(Shipping shipping) {
        shippingRepository.save(shipping);
    }

    @Override
    public List<Shipping> getAllShippings() {
        return shippingRepository.findAll();
    }

    @Override
    public Shipping findById(long id) {
        return shippingRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shipping not found"));
    }


}
