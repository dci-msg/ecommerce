package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserProfile(UserProfile userProfile);
    Address findAddressById(Long id);
}
