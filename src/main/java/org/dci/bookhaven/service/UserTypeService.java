package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.UserType;
import org.dci.bookhaven.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;


@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    @Modifying
    @Transactional
    public void createUserType(UserType userType) {
        userTypeRepository.save(userType);
    }

    public UserType getUserTypeByName(String name) {
        return userTypeRepository.findByUserTypeName(name);
    }
}
