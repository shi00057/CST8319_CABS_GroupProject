package com.example.cabs.repository.impl;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.repository.ReportMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepositoryImpl {
    private final ReportMapper mapper;

    public ReportRepositoryImpl(ReportMapper mapper) {
        this.mapper = mapper;
    }

    public List<AppointmentDto> reportDoctorAppointments(Integer doctorId, LocalDateTime fromUtc) {
        return mapper.reportDoctorAppointments(doctorId, fromUtc);
    }

    public List<Map<String, Object>> reportDoctorAppointmentsCsv(Integer doctorId, LocalDateTime fromUtc) {
        return mapper.reportDoctorAppointmentsCsv(doctorId, fromUtc);
    }

    public List<Map<String, Object>> reportDoctorTotals(Integer doctorId, LocalDateTime fromUtc) {
        return mapper.reportDoctorTotals(doctorId, fromUtc);
    }
}
