package com.example.cabs.web.controller.doctor;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public DoctorController(DoctorService doctorService, AppointmentService appointmentService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("/list")
    public List<DoctorBasicDto> listDoctors() {
        return doctorService.listDoctorsBasic();
    }

    @GetMapping("/slots")
    public List<SlotDto> listSlots(@RequestParam Integer doctorId,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate) {
        return appointmentService.listAvailableSlots(doctorId, workDate);
    }

    @PostMapping("/slots/generate")
    public ResponseEntity<Void> generateSlots(@RequestParam Integer doctorId,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                                              @RequestParam Integer startHour,
                                              @RequestParam Integer endHour,
                                              @RequestParam Integer byUserId) {
        doctorService.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointments/data")
    public List<AppointmentDto> listAppointments(@RequestParam Integer doctorId,
                                                 @RequestParam(required = false)
                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc) {
        if (fromUtc == null) {
            fromUtc = LocalDateTime.now().minusDays(30);
        }
        return appointmentService.listAppointmentsByDoctor(doctorId, fromUtc);
    }



    @PostMapping("/appointments/cancel")
    public ResponseEntity<Void> cancelAppointment(@RequestParam Long apptId,
                                                  @RequestParam Integer doctorId,
                                                  @RequestParam Integer byUserId) {
        appointmentService.cancelAppointmentForDoctor(apptId, doctorId, byUserId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody DoctorUpdateRequest request) {
        doctorService.updateDoctor(request);
        return ResponseEntity.noContent().build();
    }
}
