package com.example.cabs.web.controller.patient;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.PatientUpdateRequest;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PatientController(PatientService patientService, AppointmentService appointmentService) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestParam String email) {
        patientService.registerPatient(email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/appointments")
    public List<AppointmentDto> listAppointments(@RequestParam(required = false) Integer doctorId,
                                                 @RequestParam(required = false) Integer patientId,
                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc) {
        return appointmentService.listAppointments(doctorId, patientId, fromUtc);
    }

    @PostMapping("/book")
    public ResponseEntity<Void> book(@RequestParam Integer doctorId,
                                     @RequestParam Integer patientId,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startUtc) {
        appointmentService.bookAppointment(doctorId, patientId, startUtc);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book/by-user")
    public ResponseEntity<Void> bookByUser(@RequestParam Integer doctorId,
                                           @RequestParam Integer patientUserId,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startUtc) {
        appointmentService.bookAppointmentForPatientUser(doctorId, patientUserId, startUtc);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancel(@RequestParam Long apptId,
                                       @RequestParam Integer patientId,
                                       @RequestParam Integer byUserId) {
        appointmentService.cancelAppointment(apptId, patientId, byUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cancel/by-user")
    public ResponseEntity<Void> cancelByUser(@RequestParam Long apptId,
                                             @RequestParam Integer patientUserId,
                                             @RequestParam Integer byUserId) {
        appointmentService.cancelAppointmentForPatientUser(apptId, patientUserId, byUserId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateProfile(@RequestBody PatientUpdateRequest request) {
        patientService.updatePatient(request);
        return ResponseEntity.noContent().build();
    }
}
