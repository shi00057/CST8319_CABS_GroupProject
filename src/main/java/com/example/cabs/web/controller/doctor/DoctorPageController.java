package com.example.cabs.web.controller.doctor;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import com.example.cabs.repository.UserLookupMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/doctor")
public class DoctorPageController {
    private final DoctorService doctorService;
    private final UserLookupMapper userLookupMapper;
    private final AppointmentService appointmentService;

    public DoctorPageController(DoctorService doctorService,
                                AppointmentService appointmentService,
                                UserLookupMapper userLookupMapper) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.userLookupMapper = userLookupMapper;
    }

    @GetMapping
    public String dashboard(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        return "doctor/index";
    }

    @GetMapping("/schedule")
    public String schedulePage(@RequestParam(required = false)
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                               Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        Integer uid = userId(auth);
        Integer did = uid != null ? doctorService.getDoctorIdByUserId(uid) : null;
        LocalDate date = (workDate != null ? workDate : LocalDate.now());
        model.addAttribute("workDate", date);
        List<SlotDto> slots = (did != null) ? appointmentService.listAvailableSlots(did, date) : List.of();
        model.addAttribute("slots", slots);
        return "doctor/schedule";
    }

    @PostMapping("/schedule/generate")
    public String generateSlotsViaForm(@RequestParam
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                                       @RequestParam String dayStart,
                                       @RequestParam String dayEnd,
                                       Authentication auth,
                                       RedirectAttributes ra) {
        Integer byUserId = userId(auth);
        Integer doctorUserId = byUserId;
        Integer doctorId = (doctorUserId != null) ? doctorService.getDoctorIdByUserId(doctorUserId) : null;

        int startHour = LocalTime.parse(dayStart).getHour();
        int endHour = LocalTime.parse(dayEnd).getHour();

        if (doctorId != null) {
            doctorService.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId);
        }

        ra.addFlashAttribute("message", "Slots generated.");
        return "redirect:/doctor/schedule?workDate=" + workDate;
    }

    @GetMapping("/appointments")
    public String appointmentsPage(@RequestParam(required = false) Integer doctorId,
                                   @RequestParam(required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc,
                                   Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        if (fromUtc == null) fromUtc = LocalDateTime.now().minusDays(30);
        Integer uid = userId(auth);
        Integer did = doctorId != null ? doctorId : (uid != null ? doctorService.getDoctorIdByUserId(uid) : null);
        List<AppointmentDto> items = did != null ? appointmentService.listAppointmentsByDoctor(did, fromUtc) : List.of();
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
        return "redirect:/doctor/appointments?doctorId=" + doctorId;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Authentication auth) {
        model.addAttribute("username", auth != null ? auth.getName() : "Doctor");
        Integer uid = userId(auth);
        DoctorUpdateRequest vm = (uid != null) ? doctorService.getMyProfile(uid) : null;
        if (vm == null) vm = new DoctorUpdateRequest();
        if (vm.getDoctorId() == null && uid != null) {
            Integer did = doctorService.getDoctorIdByUserId(uid);
            vm.setDoctorId(did);
        }
        if (vm.getEmail() == null && auth != null) {
            vm.setEmail(auth.getName());
        }
        model.addAttribute("doctor", vm);
        return "doctor/profile";
    }

    @PostMapping("/profile")
    public String updateProfileViaForm(@ModelAttribute DoctorUpdateRequest request,
                                       Authentication auth,
                                       RedirectAttributes ra) {
        Integer uid = userId(auth);
        if (request.getDoctorId() == null && uid != null) {
            Integer did = doctorService.getDoctorIdByUserId(uid);
            request.setDoctorId(did);
        }
        doctorService.updateDoctor(request);
        ra.addFlashAttribute("message", "Profile updated.");
        return "redirect:/doctor/profile";
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
