package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.Users;
import org.dci.bookhaven.model.UsersType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersTypeRepository extends JpaRepository<UsersType, Long> {
    UsersType findByUserTypeName(String userTypeName);

}
