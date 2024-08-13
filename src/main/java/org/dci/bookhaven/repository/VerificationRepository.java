package org.dci.bookhaven.repository;


import org.dci.bookhaven.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken, Long> {

    // interface for managing Tokens operations
    VerificationToken findByToken(String token);
}
