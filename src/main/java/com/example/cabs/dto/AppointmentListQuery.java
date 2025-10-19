package com.example.cabs.dto;

import java.time.LocalDateTime;

public class AppointmentListQuery {
    private Integer doctorId;
    private Integer patientId;
    private LocalDateTime fromUtc;

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public Integer getPatientId() { return patientId; }
    public void setPatientId(Integer patientId) { this.patientId = patientId; }
    public LocalDateTime getFromUtc() { return fromUtc; }
    public void setFromUtc(LocalDateTime fromUtc) { this.fromUtc = fromUtc; }
}
