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
    public String viewProfile(@RequestParam (name = "id") Long id, Model model) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);
        return "viewProfile";
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
