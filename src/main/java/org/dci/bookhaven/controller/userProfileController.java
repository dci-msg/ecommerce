package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    @GetMapping("/view")
    public String viewProfile(@RequestParam Long userId, Model model) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfile);
        return "viewProfile"; // returns a view (Thymeleaf, JSP, etc.)
    }

    // GET method to show the update form
    @GetMapping("/edit")
    public String showUpdateForm(@RequestParam Long userId, Model model) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(userId);
        model.addAttribute("userProfile", userProfile);
        return "editProfile"; // returns a form view (Thymeleaf, JSP, etc.)
    }

    // POST method to handle form submission and update the profile
    @PostMapping("/update")
    public String updateProfile(@RequestParam Long userId,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                                @RequestParam String gender,
                                Model model) {
        userProfileService.updateProfile(userId, firstName, lastName, dateOfBirth, gender);
        model.addAttribute("message", "Profile updated successfully!");
        return "redirect:/profile/view?userId=" + userId;
    }

    // Managing Addresses
    @GetMapping("/addresses")
    public String viewAddresses(@RequestParam Long userId, Model model) {
        List<Address> addresses = userProfileService.getAddresses(userId);
        model.addAttribute("addresses", addresses);
        model.addAttribute("userId", userId);
        return "viewAddresses";
    }

    @GetMapping("/addresses/add")
    public String showAddAddressForm(@RequestParam Long userId, Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("userId", userId);
        return "addAddress";
    }

    @PostMapping("/addresses/add")
    public String addAddress(@RequestParam Long userId, @ModelAttribute Address address) {
        userProfileService.addAddress(userId, address);
        return "redirect:/profile/addresses?userId=" + userId;
    }

    @GetMapping("/addresses/edit")
    public String showEditAddressForm(@RequestParam Long addressId, Model model) {
        Address address = addressRepository.findById(addressId).orElse(null);
        model.addAttribute("address", address);
        return "editAddress";
    }

    @PostMapping("/addresses/edit")
    public String updateAddress(@RequestParam Long addressId, @ModelAttribute Address address) {
        userProfileService.updateAddress(addressId, address);
        return "redirect:/profile/addresses?userId=" + address.getUserProfile().getProfileId();
    }

    @PostMapping("/addresses/delete")
    public String deleteAddress(@RequestParam Long addressId, @RequestParam Long userId) {
        userProfileService.deleteAddress(addressId);
        return "redirect:/profile/addresses?userId=" + userId;
    }

}
