package com.example.cabs.repository.impl;

import com.example.cabs.repository.DoctorSelfMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class DoctorSelfRepositoryImpl {
    private final DoctorSelfMapper mapper;

    public DoctorSelfRepositoryImpl(DoctorSelfMapper mapper) {
        this.mapper = mapper;
    }

    public void doctorGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId, String source) {
        mapper.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId, source);
    }
}
