package com.pes.healthtrack.controller;

import com.pes.healthtrack.model.User;
import com.pes.healthtrack.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal; 

@Controller
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; 
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register"; 
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam User.Role role,
                               Model model) {
        try {
            authService.registerUser(name, email, password, role);
            return "redirect:/login?success"; 
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "register"; 
        }
    }

    
    @GetMapping("/dashboard")
    public String showDashboard(Principal principal) {
        if (principal == null) {
            return "redirect:/login"; 
        }
        User user = authService.getUserRepository().findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Logged in user not found in DB!"));

        if (user.getRole() == User.Role.PATIENT) {
            return "redirect:/patient/dashboard";
        } else if (user.getRole() == User.Role.DOCTOR) {
            return "redirect:/doctor/dashboard";
        }
        return "redirect:/login"; 
    }
}