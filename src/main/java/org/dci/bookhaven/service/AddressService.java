package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Address;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface AddressService  {
    @Modifying
    @Transactional
    void create(Address address);

    List<Address> getAddresses();
}
