package com.example.cabs.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SlotDto {
    private Integer doctorId;
    private LocalDate workDate;
    private LocalDateTime startUtc;
    private LocalDateTime endUtc;

    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }
    public LocalDateTime getStartUtc() { return startUtc; }
    public void setStartUtc(LocalDateTime startUtc) { this.startUtc = startUtc; }
    public LocalDateTime getEndUtc() { return endUtc; }
    public void setEndUtc(LocalDateTime endUtc) { this.endUtc = endUtc; }
}
