package com.example.cabs.web.controller.patient;

import com.example.cabs.dto.PatientRegisterRequest;
import com.example.cabs.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/patient")
public class PatientPageController {

    private final PatientService patientService;

    public PatientPageController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "patient/register";
    }

    @PostMapping("/register/submit")
    public String submitRegister(@ModelAttribute PatientRegisterRequest form,
                                 RedirectAttributes ra) {
        patientService.registerPatient(form);
        ra.addAttribute("registered", "1");
        return "redirect:/login";
    }
}
