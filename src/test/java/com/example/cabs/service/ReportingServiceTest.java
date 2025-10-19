package com.example.cabs.service;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.repository.impl.ReportRepositoryImpl;
import com.example.cabs.service.impl.ReportingServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {ReportingServiceImpl.class})
public class ReportingServiceTest {
    @MockBean
    private ReportRepositoryImpl repo;

    @Resource
    private ReportingService reportingService;

    @Test
    void reportAppointmentsDelegates() {
        Mockito.when(repo.reportDoctorAppointments(any(), any())).thenReturn(Collections.emptyList());
        List<AppointmentDto> r = reportingService.reportDoctorAppointments(1, LocalDateTime.now());
        assertEquals(0, r.size());
    }

    @Test
    void reportCsvDelegates() {
        Mockito.when(repo.reportDoctorAppointmentsCsv(any(), any())).thenReturn(Collections.emptyList());
        List<Map<String,Object>> r = reportingService.reportDoctorAppointmentsCsv(1, LocalDateTime.now());
        assertEquals(0, r.size());
    }

    @Test
    void reportTotalsDelegates() {
        Mockito.when(repo.reportDoctorTotals(any(), any())).thenReturn(Collections.emptyList());
        List<Map<String,Object>> r = reportingService.reportDoctorTotals(1, LocalDateTime.now());
        assertEquals(0, r.size());
    }
}
