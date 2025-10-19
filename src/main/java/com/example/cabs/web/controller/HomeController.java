package com.example.cabs.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/doctor")
    public String doctorPage() {
        return "doctor/index";
    }

    @GetMapping("/patient")
    public String patientPage() {
        return "patient/index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin/index";
    }
}
