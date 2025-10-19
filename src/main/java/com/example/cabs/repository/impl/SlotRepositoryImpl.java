package com.example.cabs.repository.impl;

import com.example.cabs.dto.SlotDto;
import com.example.cabs.repository.SlotMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class SlotRepositoryImpl {
    private final SlotMapper mapper;

    public SlotRepositoryImpl(SlotMapper mapper) {
        this.mapper = mapper;
    }

    public List<SlotDto> listAvailableSlots(Integer doctorId, LocalDate workDate) {
        return mapper.listAvailableSlots(doctorId, workDate);
    }

    public void clearSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate) {
        mapper.clearSlotsRange(doctorId, fromDate, toDate);
    }
}
