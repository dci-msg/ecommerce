package org.dci.bookhaven.service;

import jakarta.transaction.Transactional;
import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.repository.UserProfileRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class UserProfileService {

    @Autowired
    private final UserProfileRepository userProfileRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AddressRepository addressRepository;

    public UserProfileService(UserProfileRepository userProfileRepository, UserRepository userRepository, AddressRepository addressRepository) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

    //Managing addresses
    public List<Address> getAddresses(Long userId) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
        return userProfile.getAddresses();
    }

    public void addAddress(Long userId, Address address) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(userId);
        address.setUserProfile(userProfile);
        addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        addressRepository.deleteById(addressId);
    }

    public void updateAddress(Long addressId, Address updatedAddress) {
        Address address = addressRepository.findById(addressId).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setZipCode(updatedAddress.getZipCode());
        address.setCountry(updatedAddress.getCountry());
        addressRepository.save(address);
    }
}


