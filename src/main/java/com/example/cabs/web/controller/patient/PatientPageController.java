package com.example.cabs.web.controller.patient;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.PatientRegisterRequest;
import com.example.cabs.dto.PatientUpdateRequest;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.dto.NotificationDto;
import com.example.cabs.repository.UserLookupMapper;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import com.example.cabs.service.PatientService;
import com.example.cabs.service.NotificationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientPageController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final UserLookupMapper userLookupMapper;
    private final NotificationService notificationService;

    public PatientPageController(PatientService patientService,
                                 AppointmentService appointmentService,
                                 DoctorService doctorService,
                                 UserLookupMapper userLookupMapper,
                                 NotificationService notificationService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.userLookupMapper = userLookupMapper;
        this.notificationService = notificationService;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "public/register";
    }

    @PostMapping("/register/submit")
    public String submitRegister(@ModelAttribute PatientRegisterRequest form,
                                 RedirectAttributes ra) {
        patientService.registerPatient(form);
        return "public/register-success";
    }

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Patient");
        return "patient/index";
    }

    @GetMapping("/create-appointment")
    public String createAppointment(@RequestParam(required = false) Integer doctorId,
                                    @RequestParam(required = false)
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                                    Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Patient");
        List<DoctorBasicDto> doctors = doctorService.listDoctorsBasic();
        model.addAttribute("doctors", doctors);
        if (doctorId != null) {
            DoctorBasicDto doctor = doctors.stream().filter(d -> d.getDoctorId().equals(doctorId)).findFirst().orElse(null);
            model.addAttribute("doctor", doctor);
            model.addAttribute("workDate", workDate);
            List<SlotDto> slots = workDate != null ? appointmentService.listAvailableSlots(doctorId, workDate) : List.of();
            model.addAttribute("slots", slots);
        }
        return "patient/create-appointment";
    }

    @PostMapping("/book-form")
    public String bookViaForm(@RequestParam Integer doctorId,
                              @RequestParam String startUtc,
                              @RequestParam String endUtc,
                              Authentication auth,
                              RedirectAttributes ra) {
        Integer patientUserId = userId(auth);
        LocalDateTime start = startUtc != null && (startUtc.endsWith("Z") || startUtc.contains("+"))
                ? OffsetDateTime.parse(startUtc).toLocalDateTime()
                : LocalDateTime.parse(startUtc);
        LocalDateTime end = endUtc != null && (endUtc.endsWith("Z") || endUtc.contains("+"))
                ? OffsetDateTime.parse(endUtc).toLocalDateTime()
                : LocalDateTime.parse(endUtc);
        appointmentService.bookAppointmentForPatientUser(doctorId, patientUserId, start, end);
        ra.addFlashAttribute("message", "Appointment booked.");
        return "redirect:/patient/appointments-page";
    }

    @GetMapping("/appointments-page")
    public String appointmentsPage(@RequestParam(required = false) Integer doctorId,
                                   @RequestParam(required = false) Integer patientId,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc,
                                   Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Patient");
        if (fromUtc == null) fromUtc = LocalDateTime.now().minusDays(30);
        Integer effectivePatientId = patientId;
        List<AppointmentDto> items = appointmentService.listAppointments(doctorId, effectivePatientId, fromUtc);
        model.addAttribute("appointments", items);
        return "patient/appointments";
    }

    @PostMapping("/cancel-form")
    public String cancelViaForm(@RequestParam Long apptId,
                                Authentication auth,
                                RedirectAttributes ra) {
        Integer patientUserId = userId(auth);
        appointmentService.cancelAppointmentForPatientUser(apptId, patientUserId, patientUserId);
        ra.addFlashAttribute("message", "Appointment canceled.");
        return "redirect:/patient/appointments-page";
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Patient");
        model.addAttribute("profile", null);
        return "patient/profile";
    }

    @PostMapping("/profile")
    public String updateProfileViaForm(@ModelAttribute PatientUpdateRequest request,
                                       Authentication auth,
                                       RedirectAttributes ra) {
        patientService.updatePatient(request);
        ra.addFlashAttribute("message", "Profile updated.");
        return "redirect:/patient/profile";
    }

    @GetMapping("/notifications")
    public String notificationsPage(@RequestParam(required = false) Boolean onlyUnread,
                                    @RequestParam(required = false) Integer top,
                                    Model model,
                                    Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Patient");
        Integer uid = userId(auth);
        List<NotificationDto> items = notificationService.listForUser(uid, onlyUnread, top);
        model.addAttribute("notifications", items);
        return "patient/notifications";
    }

    @PostMapping("/notifications/mark-read")
    public String markRead(@RequestParam Long notificationId,
                           Authentication auth,
                           RedirectAttributes ra) {
        Integer uid = userId(auth);
        notificationService.markRead(uid, notificationId);
        ra.addFlashAttribute("message", "Marked as read.");
        return "redirect:/patient/notifications";
    }

    private Integer userId(Authentication auth) {
        if (auth == null) return null;
        Object p = auth.getPrincipal();
        if (p instanceof com.example.cabs.domain.User u) {
            Long v = u.getUserId();
            return v == null ? null : v.intValue();
        }
        String login = auth.getName();
        return userLookupMapper.findUserIdByLogin(login);
    }
}
