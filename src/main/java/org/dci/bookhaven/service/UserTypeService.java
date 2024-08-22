package org.dci.bookhaven.service;

import org.dci.bookhaven.model.UserType;
import org.dci.bookhaven.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;

    @Autowired
    public UserTypeService(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    // to get all the users type from the db
    public List<UserType> getAll(){
        return userTypeRepository.findAll();
    }
}
