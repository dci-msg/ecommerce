package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class userProfileController {
    //Controller class for user profile
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private AddressRepository addressRepository;

    // GET method to display the user's profile
    @GetMapping("/view/{id}")
    public String viewProfile(Model model, @PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);

        // Check if the user profile is incomplete of personal details are null
        if (userProfile.getFirstName() == null || userProfile.getLastName() == null ||
                userProfile.getDateOfBirth() == null || userProfileService.getAddresses(id).isEmpty()) {
            return "redirect:/profile/complete?id=" + id;
        }

        model.addAttribute("userProfile", userProfile);
        return "viewProfile";
    }

 /*   @GetMapping
    public String userProfileHome(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserProfile profile = userProfileService.getUserProfileByUserId(userDetails.getUserId());
        model.addAttribute("user", profile.getUser());
        model.addAttribute("profile", profile);
        return "userProfile";
    }*/

    // GET method to display the form to complete profile information
    @GetMapping("/complete")
    public String showCompleteProfileForm(@RequestParam Long id, Model model) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("address", new Address());
        return "completeProfile";
    }

    // POST method to handle form submission and save the profile details
    @PostMapping("/complete")
    public String completeProfile(@RequestParam Long id,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                  @RequestParam String gender,
                                  @ModelAttribute Address address) {
        // Update user profile with the provided information
        userProfileService.updateProfile(id, firstName, lastName, dateOfBirth, gender);

        // Add address to the user profile
        userProfileService.addAddress(id, address);

        return "redirect:/profile/view?id=" + id;
    }


    // GET method to show the update form
    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam Long id, Model model) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);
        return "editProfile";
    }

    // POST method to handle form submission and update the profile
    @PostMapping("/update")
    public String updateProfile(@RequestParam Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                @RequestParam String gender,
                                Model model) {
        userProfileService.updateProfile(id, firstName, lastName, dateOfBirth, gender);
        model.addAttribute("message", "Profile updated successfully!");
        return "redirect:/profile/view?id=" + id;
    }

    // Managing Addresses
    @GetMapping("/addresses")
    public String viewAddresses(@RequestParam Long id, Model model) {
        List<Address> addresses = userProfileService.getAddresses(id);
        model.addAttribute("addresses", addresses);
        model.addAttribute("id", id);
        return "viewAddresses";
    }

    @GetMapping("/addresses/add")
    public String showAddAddressForm(@RequestParam Long id, Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("id", id);
        return "addAddress";
    }

    @PostMapping("/addresses/add")
    public String addAddress(@RequestParam Long id, @ModelAttribute Address address) {
        userProfileService.addAddress(id, address);
        return "redirect:/profile/addresses?id=" + id;
    }

    @GetMapping("/addresses/edit")
    public String showEditAddressForm(@RequestParam Long id, Model model) {
        Address address = addressRepository.findById(id).orElse(null);
        model.addAttribute("address", address);
        return "editAddress";
    }

    @PostMapping("/addresses/edit")
    public String updateAddress(@RequestParam Long id, @ModelAttribute Address address) {
        userProfileService.updateAddress(id, address);
        return "redirect:/profile/addresses?id=" + address.getUserProfile().getId();
    }

    @PostMapping("/addresses/delete")
    public String deleteAddress(@RequestParam Long id) {
        userProfileService.deleteAddress(id);
        return "redirect:/profile/addresses?id=" + id;
    }

}
