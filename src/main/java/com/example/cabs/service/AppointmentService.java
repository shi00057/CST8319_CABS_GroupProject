package com.example.cabs.service;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.SlotDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {
    List<SlotDto> listAvailableSlots(Integer doctorId, LocalDate workDate);
    void clearSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate);
    void bookAppointment(Integer doctorId, Integer patientId, LocalDateTime startUtc);
    void bookAppointmentForPatientUser(Integer doctorId, Integer patientUserId, LocalDateTime startUtc);
    void cancelAppointment(Long apptId, Integer patientId, Integer byUserId);
    void cancelAppointmentForPatientUser(Long apptId, Integer patientUserId, Integer byUserId);
    List<AppointmentDto> listAppointments(Integer doctorId, Integer patientId, LocalDateTime fromUtc);
    List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc);
    List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc);
}
