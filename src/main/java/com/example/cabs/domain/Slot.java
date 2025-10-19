package com.example.cabs.domain;

import java.time.LocalDateTime;

public class Slot {
    private Long slotId;
    private Integer doctorId;
    private LocalDateTime startUtc;
    private LocalDateTime endUtc;
    private Boolean booked;

    public Long getSlotId() { return slotId; }
    public void setSlotId(Long slotId) { this.slotId = slotId; }
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(Integer doctorId) { this.doctorId = doctorId; }
    public LocalDateTime getStartUtc() { return startUtc; }
    public void setStartUtc(LocalDateTime startUtc) { this.startUtc = startUtc; }
    public LocalDateTime getEndUtc() { return endUtc; }
    public void setEndUtc(LocalDateTime endUtc) { this.endUtc = endUtc; }
    public Boolean getBooked() { return booked; }
    public void setBooked(Boolean booked) { this.booked = booked; }
}
