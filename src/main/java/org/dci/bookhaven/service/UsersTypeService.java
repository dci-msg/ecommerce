package org.dci.bookhaven.service;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.model.UsersType;
import org.dci.bookhaven.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersTypeService {

    private final UsersTypeRepository usersTypeRepository;

    @Autowired
    public UsersTypeService(UsersTypeRepository usersTypeRepository) {
        this.usersTypeRepository = usersTypeRepository;
    }

    // to get all the users type from the db
    public List<UsersType> getAll(){
        return usersTypeRepository.findAll();
    }
}
