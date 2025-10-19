package com.example.cabs.dto;

import java.time.LocalDateTime;

public class AppointmentDto {
    private Long apptId;
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime startUtc;
    private LocalDateTime endUtc;
    private String status;

    public Long getApptId() { return apptId; }
    public void setApptId(Long apptId) { this.apptId = apptId; }
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    public LocalDateTime getStartUtc() { return startUtc; }
    public void setStartUtc(LocalDateTime startUtc) { this.startUtc = startUtc; }
    public LocalDateTime getEndUtc() { return endUtc; }
    public void setEndUtc(LocalDateTime endUtc) { this.endUtc = endUtc; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
