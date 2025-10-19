package com.example.cabs.repository;

import com.example.cabs.dto.AppointmentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface ReportMapper {
    List<AppointmentDto> reportDoctorAppointments(@Param("p_DoctorId") Integer doctorId,
                                                  @Param("p_FromUtc") LocalDateTime fromUtc);
    List<Map<String,Object>> reportDoctorAppointmentsCsv(@Param("p_DoctorId") Integer doctorId,
                                                         @Param("p_FromUtc") LocalDateTime fromUtc);
    List<Map<String,Object>> reportDoctorTotals(@Param("p_DoctorId") Integer doctorId,
                                                @Param("p_FromUtc") LocalDateTime fromUtc);
}
