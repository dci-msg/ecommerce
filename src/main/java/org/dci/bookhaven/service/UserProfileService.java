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
    public UserProfile getLoggedInUserProfile(Long id) {
        return userProfileRepository.findUserProfileByUserId(id);
    }

    // Method to update the profile of a user with specified details
    public void updateProfile(Long id, String firstName, String lastName, LocalDate dateOfBirth, String gender) {
        // Find the user by ID
        User user = userRepository.findById(id).orElse(null);

        // Check if the user exists
        if (user == null) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }

        // Find the user's profile by ID
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(id);

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

    public UserProfile getUserProfileByUserId(Long id) {
        return userProfileRepository.findUserProfileByUserId(id);
    }

    //Managing addresses
    public List<Address> getAddresses(Long id) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(id);
        return userProfile.getAddresses();
    }

    public void addAddress(Long id, Address address) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserId(id);
        address.setUserProfile(userProfile);
        addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        addressRepository.deleteById(id);
    }

    public void updateAddress(Long id, Address updatedAddress) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Address not found"));
        address.setStreet(updatedAddress.getStreet());
        address.setCity(updatedAddress.getCity());
        address.setZipCode(updatedAddress.getZipCode());
        address.setCountry(updatedAddress.getCountry());
        addressRepository.save(address);
    }
}


