package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u.email FROM User u JOIN UserType ut  ON u.userType.id = ut.id WHERE ut.userTypeName = 'Admin'")
    List<String> findAllAdminEmails();
}
