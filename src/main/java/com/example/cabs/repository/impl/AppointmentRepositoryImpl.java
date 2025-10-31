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

    public void bookAppointmentV2(Integer doctorId, Integer patientId, LocalDateTime startUtc, LocalDateTime endUtc, Integer byUserId) {
        mapper.bookAppointmentV2(doctorId, patientId, startUtc, endUtc, byUserId);
    }

    public void cancelAppointment(Long apptId, Integer patientId, Integer byUserId) {
        mapper.cancelAppointment(apptId, patientId, byUserId);
    }

//        public List<AppointmentDto> listAppointments(Integer doctorId, Integer patientId, LocalDateTime fromUtc) {
//            return mapper.listAppointments(doctorId, patientId, fromUtc);
//        }

        public List<AppointmentDto> listAppointments(Integer doctorId, Integer patientId, LocalDateTime fromUtc) {
            LocalDateTime toUtc = fromUtc.plusYears(1);
            return mapper.listAppointmentsV2(doctorId, patientId, fromUtc, toUtc);
        }


//    public List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc) {
//        return mapper.listAppointmentsByDoctor(doctorId, fromUtc);
//    }
//
//    public List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc) {
//        return mapper.listAppointmentsByPatient(patientId, fromUtc);
//    }
    public List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc) {
        LocalDateTime toUtc = fromUtc.plusYears(1);
        return mapper.listAppointmentsByDoctorV2(doctorId, fromUtc, toUtc);
    }
    public List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc) {
        LocalDateTime toUtc = fromUtc.plusYears(1);
        return mapper.listAppointmentsByPatientV2(patientId, fromUtc, toUtc);
    }


    public void cancelAppointmentByDoctor(Long apptId, Integer doctorId, Integer byUserId) {
        mapper.cancelAppointmentByDoctor(apptId, doctorId, byUserId);
    }

    public void cancelAppointmentByAdmin(Long apptId, Integer adminUserId, String reason) {
        mapper.cancelAppointmentByAdmin(apptId, adminUserId, reason);
    }


}
