package com.example.cabs.web.controller;

import com.example.cabs.web.controller.admin.AdminController;
import com.example.cabs.service.AppointmentService;
import com.example.cabs.service.DoctorService;
import com.example.cabs.service.PatientService;
import com.example.cabs.service.ReportingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WithMockUser(username = "admin@cst8319.com", roles = "ADMIN")
@WebMvcTest(AdminController.class)
public class AdminControllerWebTest {
    @Autowired
    private MockMvc mvc;

    @MockitoBean private DoctorService doctorService;
    @MockitoBean private AppointmentService appointmentService;
    @MockitoBean private PatientService patientService;
    @MockitoBean private ReportingService reportingService;


    @Test
    void listDoctorsOk() throws Exception {
        when(doctorService.listDoctorsBasic()).thenReturn(Collections.emptyList());
        mvc.perform(get("/admin/doctors"))
                .andExpect(status().isOk());
    }

    @Test
    void generateSlotsNoContent() throws Exception {
        mvc.perform(post("/admin/slots/generate")

                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .param("doctorId","1")
                        .param("workDate", LocalDate.now().toString())
                        .param("startHour","9")
                        .param("endHour","17")
                        .param("adminUserId","1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void reportTotalsOk() throws Exception {
        when(reportingService.reportDoctorTotals(any(), any()))
                .thenReturn(Collections.emptyList());
        mvc.perform(get("/admin/report/doctor-totals")
                        .param("doctorId","1")
                        .param("fromUtc", LocalDateTime.now().toString()))
                .andExpect(status().isOk());
    }
}
