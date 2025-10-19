package com.example.cabs.service.impl;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.repository.impl.ReportRepositoryImpl;
import com.example.cabs.service.ReportingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final ReportRepositoryImpl repo;

    public ReportingServiceImpl(ReportRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public List<AppointmentDto> reportDoctorAppointments(Integer doctorId, LocalDateTime fromUtc) {
        return repo.reportDoctorAppointments(doctorId, fromUtc);
    }

    @Override
    public List<Map<String, Object>> reportDoctorAppointmentsCsv(Integer doctorId, LocalDateTime fromUtc) {
        return repo.reportDoctorAppointmentsCsv(doctorId, fromUtc);
    }

    @Override
    public List<Map<String, Object>> reportDoctorTotals(Integer doctorId, LocalDateTime fromUtc) {
        return repo.reportDoctorTotals(doctorId, fromUtc);
    }
}
