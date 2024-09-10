package org.dci.bookhaven.service.impl;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Modifying
    @Transactional
    @Override
    public void create(Address address) {
        addressRepository.save(address);
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

}
