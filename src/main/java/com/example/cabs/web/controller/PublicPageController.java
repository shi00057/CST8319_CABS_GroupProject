// PublicPageController.java
package com.example.cabs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.cabs.dto.PatientRegisterRequest;
import com.example.cabs.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PublicPageController {
    private final PatientService patientService;

    public PublicPageController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/public/register")
    public String registerForm() { return "public/register"; }

    @PostMapping("/public/register/submit")
    public String registerSubmit(@ModelAttribute PatientRegisterRequest req, Model model) {
        try {
            patientService.registerPatient(req);
            return "redirect:/public/register-success";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("fullName", req.getFullName());
            model.addAttribute("email", req.getEmail());
            model.addAttribute("phone", req.getPhone());
            return "public/register";
        }
    }

    @GetMapping("/public/register-success")
    public String registerSuccess() { return "public/register-success"; }

    @GetMapping("/public/contact")
    public String contact() { return "public/contact"; }
}
