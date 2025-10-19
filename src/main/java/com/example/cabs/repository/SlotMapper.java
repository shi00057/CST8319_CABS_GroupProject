package com.example.cabs.repository;

import com.example.cabs.dto.SlotDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SlotMapper {
    List<SlotDto> listAvailableSlots(@Param("p_DoctorId") Integer doctorId, @Param("p_WorkDate") LocalDate workDate);
    int clearSlotsRange(@Param("p_DoctorId") Integer doctorId, @Param("p_FromDate") LocalDate fromDate, @Param("p_ToDate") LocalDate toDate);
}
