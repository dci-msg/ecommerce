package org.dci.bookhaven.controller;

import org.dci.bookhaven.model.Address;
import org.dci.bookhaven.model.User;
import org.dci.bookhaven.model.UserProfile;
import org.dci.bookhaven.repository.AddressRepository;
import org.dci.bookhaven.repository.UserProfileRepository;
import org.dci.bookhaven.repository.UserRepository;
import org.dci.bookhaven.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class UserProfileController {
    //Controller class for user profile
    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    // GET method to display the user's profile
    @GetMapping("/view/{id}")
    public String viewProfile(Model model, @PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);

        // Add the userProfile object to the model so it can be accessed in the Thymeleaf template
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

    @GetMapping()
    public String userProfile(Principal principal, Model model) {
        System.out.println("HELLO USER ");
        String loggedInUserEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInUserEmail);
        System.out.println(user);

        // Fetch user profile based on the user
        UserProfile userProfile = userProfileService.getUserProfileByUserId(user.getId());

        // Add user and userProfile to the model
        model.addAttribute("user", user);
        model.addAttribute("userProfile", userProfile);

        /*User user = (User) principal;
        System.out.println(user);*/
        return "userProfile";
    }

    /* UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);*/

    // GET method to display the form to complete user's profile information
    @GetMapping("/complete/{id}")
    public String showCompleteProfileForm(Model model, @PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);
        model.addAttribute("address", userProfile.getAddresses().get(0));
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

        userProfileService.addAddress(id, address);
        System.out.println(address);
        // Update user profile with the provided information
        userProfileService.updateProfile(id, firstName, lastName, dateOfBirth, gender);

        // Add address to the user profile
        /*userProfileService.addAddress(id, address);*/
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        return "redirect:/profile/view/" + userProfile.getUser().getId();
    }


    // GET method to show the update form
    @GetMapping("/edit/{id}")
    public String showUpdateForm(Model model, @PathVariable Long id) {
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        model.addAttribute("userProfile", userProfile);
        System.out.println(userProfile);
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

        UserProfile existingProfile = userProfileRepository.findById(id).orElse(null);
        if (existingProfile != null) {
            existingProfile.setFirstName(firstName);
            existingProfile.setLastName(lastName);
            existingProfile.setDateOfBirth(dateOfBirth);
            existingProfile.setGender(gender);
            userProfileRepository.save(existingProfile);
        }
        UserProfile userProfile = userProfileService.getUserProfileById(id);
        return "redirect:/profile/view/" + userProfile.getUser().getId();
    }

    // Managing Addresses
    @GetMapping("/addresses/{id}")
    public String viewAddresses(Model model, @PathVariable Long id) {
        System.out.println("User ID: " + id);
        List<Address> addresses = userProfileService.getAddresses(id);
        UserProfile userProfile = userProfileService.getUserProfileByUserId(id);
        System.out.println(id);
        System.out.println(userProfile.getUser());
        model.addAttribute("userProfile", userProfile.getUser());
        model.addAttribute("addresses", addresses);
        return "viewAddresses";
    }

    @GetMapping("/addresses")
    public String redirectToAddressesWithId(Principal principal, Model model) {
        String loggedInUserEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInUserEmail);
        UserProfile userProfile = userProfileService.getUserProfileByUserId(user.getId());
        return "redirect:/profile/addresses/" + userProfile.getId();
    }

    @GetMapping("/addresses/add")
    public String showAddAddressForm(@RequestParam Long id, Model model) {
        model.addAttribute("address", new Address());
        model.addAttribute("userId", id);
        return "addAddress";
    }

    @PostMapping("/addresses/add")
    public String addAddress(@RequestParam Long id, @ModelAttribute Address address) {
        System.out.println(id);
        System.out.println(address);
        System.out.println("-------");
        System.out.println("-------");
        System.out.println("-------");
        userProfileService.addAddress(id, address);
        return "redirect:/profile/addresses/" + id;
    }

    @GetMapping("/addresses/edit/{id}")
    public String showEditAddressForm(Model model, @PathVariable Long id, Principal principal) {

        String loggedInUserEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInUserEmail);

        Address address = addressRepository.findById(id).orElse(null);
        UserProfile userProfile = userProfileService.getUserProfileByUserId(user.getId());
        model.addAttribute("userProfile", userProfile.getUser());
        model.addAttribute("address", address);
//        model.addAttribute("userId", address.getUserProfile().getUser().getId());
        return "editAddress";
    }

    @PostMapping("/addresses/edit")
    public String updateAddress(@RequestParam Long id, @ModelAttribute Address address, Principal principal) {
        String loggedInUserEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInUserEmail);

        Address existingAddress = addressRepository.findById(id).orElse(null);

        if (existingAddress != null) {
            address.setUserProfile(existingAddress.getUserProfile()); // Set the UserProfile from the existing address
            userProfileService.updateAddress(id, address);
            System.out.println("Updated Address ID: " + id);
            System.out.println("User Profile ID: " + address.getUserProfile().getId());
        }

        return "redirect:/profile/addresses/" + user.getId();
    }

    @GetMapping("/addresses/delete/{id}")
    public String deleteAddress(@PathVariable Long id, Principal principal) {
        String loggedInUserEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInUserEmail);

        userProfileService.deleteAddress(id);
        return "redirect:/profile/addresses/" + user.getId();
    }

}
