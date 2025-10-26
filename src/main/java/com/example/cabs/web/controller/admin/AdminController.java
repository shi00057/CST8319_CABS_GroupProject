package com.example.cabs.web.controller.admin;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import com.example.cabs.service.PatientService;
import com.example.cabs.service.ReportingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PatientService patientService;
    private final ReportingService reportingService;

    public AdminController(DoctorService doctorService,
                           AppointmentService appointmentService,
                           PatientService patientService,
                           ReportingService reportingService) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.patientService = patientService;
        this.reportingService = reportingService;
    }

    // --- Doctors
    @GetMapping("/doctors")
    public List<DoctorBasicDto> listDoctors() {
        return doctorService.listDoctorsBasic();
    }

    @PostMapping("/doctor")
    public ResponseEntity<Void> createDoctor(@RequestParam String email) {
        doctorService.createDoctor(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/doctor")
    public ResponseEntity<Void> updateDoctor(@RequestBody DoctorUpdateRequest request) {
        doctorService.updateDoctor(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer doctorId) {
        doctorService.deleteDoctorSoft(doctorId);
        return ResponseEntity.noContent().build();
    }

    // --- Slots (admin)
    @PostMapping("/slots/generate")
    public ResponseEntity<Void> adminGenerateSlots(@RequestParam Integer doctorId,
                                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate workDate,
                                                   @RequestParam Integer startHour,
                                                   @RequestParam Integer endHour,
                                                   @RequestParam Integer adminUserId) {
        doctorService.adminGenerateSlots(doctorId, workDate, startHour, endHour, adminUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/slots/generate-range")
    public ResponseEntity<Void> adminGenerateSlotsRange(@RequestParam Integer doctorId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                                                        @RequestParam Integer startHour,
                                                        @RequestParam Integer endHour,
                                                        @RequestParam Integer adminUserId) {
        doctorService.adminGenerateSlotsRange(doctorId, fromDate, toDate, startHour, endHour, adminUserId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/slots/clear")
    public ResponseEntity<Void> clearSlotsRange(@RequestParam Integer doctorId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        appointmentService.clearSlotsRange(doctorId, fromDate, toDate);
        return ResponseEntity.noContent().build();
    }

    // --- Patients activation
    @GetMapping("/patients/pending")
    public List<PatientActivationDto> pendingPatients() {
        return patientService.listPatientsPendingActivation();
    }

    @PostMapping("/patients/activate")
    public ResponseEntity<Void> activateUser(@RequestParam Integer userId, @RequestParam Boolean isActive) {
        patientService.activateUser(userId, isActive);
        return ResponseEntity.noContent().build();
    }

    // --- Reports (doctor-centric)
    @GetMapping("/report/doctor-appointments")
    public List<AppointmentDto> reportDoctorAppointments(@RequestParam Integer doctorId,
                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc) {
        return reportingService.reportDoctorAppointments(doctorId, fromUtc);
    }

    @GetMapping("/report/doctor-appointments/csv")
    public List<Map<String, Object>> reportDoctorAppointmentsCsv(@RequestParam Integer doctorId,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc) {
        return reportingService.reportDoctorAppointmentsCsv(doctorId, fromUtc);
    }

    @GetMapping("/report/doctor-totals")
    public List<Map<String, Object>> reportDoctorTotals(@RequestParam Integer doctorId,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromUtc) {
        return reportingService.reportDoctorTotals(doctorId, fromUtc);
    }
}
