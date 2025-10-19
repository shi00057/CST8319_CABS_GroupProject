package com.example.cabs.service;

import com.example.cabs.dto.AppointmentDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportingService {
    List<AppointmentDto> reportDoctorAppointments(Integer doctorId, LocalDateTime fromUtc);
    List<Map<String,Object>> reportDoctorAppointmentsCsv(Integer doctorId, LocalDateTime fromUtc);
    List<Map<String,Object>> reportDoctorTotals(Integer doctorId, LocalDateTime fromUtc);
}
