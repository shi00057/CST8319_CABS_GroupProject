package com.example.cabs.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String root(Authentication auth) {
        return "index";
    }

    @GetMapping("/portal")
    public String portal(Authentication auth) {
        if (auth == null) return "redirect:/login";
        boolean admin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean doctor = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"));
        boolean patient = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PATIENT"));
        if (admin) return "redirect:/admin";
        if (doctor) return "redirect:/doctor";
        if (patient) return "redirect:/patient";
        return "index";
    }

    @GetMapping("/admin")
    public String adminIndex() { return "admin/index"; }

    @GetMapping("/doctor")
    public String doctorIndex() { return "doctor/index"; }

    @GetMapping("/patient")
    public String patientIndex() { return "patient/index"; }
}
