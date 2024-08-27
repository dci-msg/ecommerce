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

    private AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Modifying
    @Transactional
    public void createAddress(Address address) {
        if(addressRepository.existsById(address.getId())) {
            throw new IllegalStateException("Address already exists");
        } else{
            addressRepository.save(address);
        }
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository.findAddressById(id);
    }

    @Override
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }

}
