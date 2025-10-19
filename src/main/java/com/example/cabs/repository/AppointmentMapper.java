package com.example.cabs.repository;

import com.example.cabs.dto.AppointmentDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface AppointmentMapper {
    int bookAppointment(@Param("p_DoctorId") Integer doctorId,
                        @Param("p_PatientId") Integer patientId,
                        @Param("p_StartUtc") LocalDateTime startUtc);
    int cancelAppointment(@Param("p_ApptId") Long apptId,
                          @Param("p_PatientId") Integer patientId,
                          @Param("p_ByUserId") Integer byUserId);
    List<AppointmentDto> listAppointments(@Param("p_DoctorId") Integer doctorId,
                                          @Param("p_PatientId") Integer patientId,
                                          @Param("p_FromUtc") LocalDateTime fromUtc);
    List<AppointmentDto> listAppointmentsByDoctor(@Param("p_DoctorId") Integer doctorId,
                                                  @Param("p_FromUtc") LocalDateTime fromUtc);
    List<AppointmentDto> listAppointmentsByPatient(@Param("p_PatientId") Integer patientId,
                                                   @Param("p_FromUtc") LocalDateTime fromUtc);
}
