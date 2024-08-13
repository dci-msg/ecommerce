package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
    }

    //Method to get User logged in at that point in time
    public UserProfile getLoggedInUserProfile(Long userId) {
        return userProfileRepository.findUserProfileByUserId(userId);
    }

    // Method to update the profile of a user with specified details
    public void updateProfile(Long userId, String firstName, String lastName, LocalDate dateOfBirth, String gender) {
        // Find the user by ID
        User user = userRepository.findById(userId).orElse(null);

        // Check if the user exists
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        // Find the user's profile by user ID
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);

        // If the userProfile exists, update the profile details
        if (userProfile != null) {
            userProfile.setFirstName(firstName);
            userProfile.setLastName(lastName);
            userProfile.setDateOfBirth(dateOfBirth);
            userProfile.setGender(gender);
        } else {
            // If userProfile doesn't exist, create a new one
            userProfile = new UserProfile();
            userProfile.setFirstName(firstName);
            userProfile.setLastName(lastName);
            userProfile.setDateOfBirth(dateOfBirth);
            userProfile.setGender(gender);
            userProfile.setUser(user);
        }

        // Save the updated userProfile to the repository
        userProfileRepository.save(userProfile);
    }

    public UserProfile getUserProfileByUserId(Long userId) {
        return userProfileRepository.findUserProfileByUserId(userId);
    }
}
