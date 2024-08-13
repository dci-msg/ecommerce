package org.dci.bookhaven.repository;

import org.dci.bookhaven.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // No need to define custom query methods for basic CRUD operations

    UserProfile findUserProfileByUserId(Long userId);
}
