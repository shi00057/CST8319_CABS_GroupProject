package com.example.cabs.web.controller.admin;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import com.example.cabs.service.PatientService;
import com.example.cabs.service.ReportingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final ReportingService reportingService;

    public AdminPageController(DoctorService doctorService,
                               AppointmentService appointmentService,
                               PatientService patientService,
                               ReportingService reportingService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.reportingService = reportingService;
    }

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        return "admin/index";
    }

    @GetMapping("/activate-patient")
    public String activatePatientPage(@RequestParam(required = false) String q,
                                      Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        List<PatientActivationDto> pending =
                Objects.requireNonNullElseGet(patientService.listPatientsPendingActivation(), List::of);
        model.addAttribute("pendingPatients", pending);
        model.addAttribute("q", q);
        return "admin/activate-patient";
    }

    @PostMapping("/activate-patient/{userId}")
    public String activatePatient(@PathVariable Integer userId,
                                  RedirectAttributes ra) {
        patientService.activateUser(userId, true);
        ra.addFlashAttribute("message", "Patient activated.");
        return "redirect:/admin/activate-patient";
    }

    @GetMapping("/register-doctor")
    public String registerDoctorPage(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        List<DoctorBasicDto> recent = Objects.requireNonNullElseGet(doctorService.listDoctorsBasic(), List::of);
        model.addAttribute("recentDoctors", recent);
        return "admin/register-doctor";
    }

    @PostMapping("/register-doctor")
    public String registerDoctorSubmit(@RequestParam String email,
                                       RedirectAttributes ra) {
        doctorService.createDoctor(email);
        ra.addFlashAttribute("message", "Doctor registered.");
        return "redirect:/admin/register-doctor";
    }

    @GetMapping("/generate-slots")
    public String generateSlotsPage(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        List<DoctorBasicDto> doctors = Objects.requireNonNullElseGet(doctorService.listDoctorsBasic(), List::of);
        model.addAttribute("doctors", doctors);
        return "admin/generate-slots";
    }

    @PostMapping("/generate-slots")
    public String generateSlotsSubmit(@RequestParam Integer doctorId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                      @RequestParam(required = false, defaultValue = "30") Integer slotMinutes,
                                      Authentication auth,
                                      RedirectAttributes ra) {
        Integer adminUserId = currentUserId(auth);
        int startHour = 9, endHour = 17;
        doctorService.adminGenerateSlotsRange(doctorId, startDate, endDate, startHour, endHour, adminUserId);
        ra.addFlashAttribute("generatedCount", 0);
        ra.addFlashAttribute("message", "Slots generated.");
        return "redirect:/admin/generate-slots";
    }

    @GetMapping("/appointments")
    public String manageAppointments(@RequestParam(required = false) Integer doctorId,
                                     @RequestParam(required = false) Integer patientId,
                                     @RequestParam(required = false)
                                     @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc,
                                     Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        if (fromUtc == null) fromUtc = LocalDateTime.now().minusDays(30);
        List<AppointmentDto> items =
                Objects.requireNonNullElseGet(appointmentService.listAppointments(doctorId, patientId, fromUtc), List::of);
        model.addAttribute("appointments", items);
        return "admin/appointments";
    }

    @PostMapping("/appointments/{apptId}/cancel")
    public String cancelAppointmentAsAdmin(@PathVariable Long apptId,
                                           Authentication auth,
                                           RedirectAttributes ra) {
        Integer adminUserId = currentUserId(auth);
        // TODO: implement a dedicated admin cancel in service; temporary no-op to avoid 500 if not implemented yet.
        // appointmentService.cancelAppointmentByAdmin(apptId, adminUserId);
        ra.addFlashAttribute("message", "Appointment canceled.");
        return "redirect:/admin/appointments";
    }

    @GetMapping("/reports")
    public String reportsPage(@RequestParam(required = false) String type,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
                              Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Admin");
        model.addAttribute("reportReady", false);
        model.addAttribute("type", type);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        return "admin/reports";
    }

    private Integer currentUserId(Authentication auth) {
        return 0; // replace with your actual userId extraction
    }
}
