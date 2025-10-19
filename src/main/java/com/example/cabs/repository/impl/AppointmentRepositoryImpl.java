package com.example.cabs.repository.impl;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.repository.AppointmentMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AppointmentRepositoryImpl {
    private final AppointmentMapper mapper;

    public AppointmentRepositoryImpl(AppointmentMapper mapper) {
        this.mapper = mapper;
    }

    public void bookAppointment(Integer doctorId, Integer patientId, LocalDateTime startUtc) {
        mapper.bookAppointment(doctorId, patientId, startUtc);
    }

    public void cancelAppointment(Long apptId, Integer patientId, Integer byUserId) {
        mapper.cancelAppointment(apptId, patientId, byUserId);
    }

    public List<AppointmentDto> listAppointments(Integer doctorId, Integer patientId, LocalDateTime fromUtc) {
        return mapper.listAppointments(doctorId, patientId, fromUtc);
    }

    public List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc) {
        return mapper.listAppointmentsByDoctor(doctorId, fromUtc);
    }

    public List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc) {
        return mapper.listAppointmentsByPatient(patientId, fromUtc);
    }
}
