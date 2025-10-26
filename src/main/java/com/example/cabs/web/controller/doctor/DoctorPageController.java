package com.example.cabs.web.controller.doctor;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorPageController {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public DoctorPageController(DoctorService doctorService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        return "doctor/index";
    }

    @GetMapping("/schedule")
    public String schedulePage(@RequestParam(required = false) Integer doctorId,
                               @RequestParam(required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                               Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        if (doctorId != null && workDate != null) {
            List<SlotDto> slots = appointmentService.listAvailableSlots(doctorId, workDate);
            model.addAttribute("slots", slots);
        }
        return "doctor/schedule";
    }

    @PostMapping("/schedule/generate")
    public String generateSlotsViaForm(@RequestParam Integer doctorId,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                       @RequestParam String dayStart,
                                       @RequestParam String dayEnd,
                                       @RequestParam(required = false) Integer slotMinutes,
                                       @RequestParam(name = "days", required = false) List<Integer> days,
                                       Authentication auth,
                                       RedirectAttributes ra) {
        Integer byUserId = userId(auth);
        if (days == null || days.isEmpty()) {
            days = Arrays.asList(1, 2, 3, 4, 5);
        }
        int startHour = LocalTime.parse(dayStart).getHour();
        int endHour = LocalTime.parse(dayEnd).getHour();
        LocalDate d = startDate;
        while (!d.isAfter(endDate)) {
            int dow = d.getDayOfWeek().getValue();
            if (days.contains(dow)) {
                doctorService.doctorGenerateSlots(doctorId, d, startHour, endHour, byUserId);
            }
            d = d.plusDays(1);
        }
        ra.addFlashAttribute("message", "Slots generated.");
        return "redirect:/doctor/schedule?doctorId=" + doctorId + "&workDate=" + startDate;
    }

    @GetMapping("/appointments-page")
    public String appointmentsPage(@RequestParam Integer doctorId,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc,
                                   Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        if (fromUtc == null) fromUtc = LocalDateTime.now().minusDays(30);
        List<AppointmentDto> items = appointmentService.listAppointments(doctorId, null, fromUtc);
        model.addAttribute("appointments", items);
        return "doctor/appointments";
    }

    @PostMapping("/appointments/{id}/cancel")
    public String cancelAppointmentViaForm(@PathVariable("id") Long apptId,
                                           @RequestParam Integer doctorId,
                                           Authentication auth,
                                           RedirectAttributes ra) {
        Integer byUserId = userId(auth);
        appointmentService.cancelAppointmentForDoctor(apptId, doctorId, byUserId);
        ra.addFlashAttribute("message", "Appointment canceled.");
        return "redirect:/doctor/appointments-page?doctorId=" + doctorId;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        model.addAttribute("doctor", null);
        return "doctor/profile";
    }

    @PostMapping("/profile")
    public String updateProfileViaForm(@ModelAttribute DoctorUpdateRequest request,
                                       Authentication auth,
                                       RedirectAttributes ra) {
        doctorService.updateDoctor(request);
        ra.addFlashAttribute("message", "Profile updated.");
        return "redirect:/doctor/profile";
    }

    private Integer userId(Authentication auth) {
        return null;
    }
}
