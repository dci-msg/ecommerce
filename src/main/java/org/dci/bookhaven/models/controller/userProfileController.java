package org.dci.bookhaven.models.controller;

import org.dci.bookhaven.models.UserProfile;
import org.dci.bookhaven.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/profile")
public class userProfileController {
    //Controller class for user profile
    @Autowired
    private UserProfileService userProfileService;


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


}
