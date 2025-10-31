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

    int bookAppointmentV2(@Param("p_DoctorId") Integer doctorId,
                          @Param("p_PatientId") Integer patientId,
                          @Param("p_StartUtc") LocalDateTime startUtc,
                          @Param("p_EndUtc") LocalDateTime endUtc,
                          @Param("p_ByUserId") Integer byUserId);

    int cancelAppointment(@Param("p_ApptId") Long apptId,
                          @Param("p_PatientId") Integer patientId,
                          @Param("p_ByUserId") Integer byUserId);

    List<AppointmentDto> listAppointmentsV2(@Param("p_DoctorId") Integer doctorId,
                                            @Param("p_PatientId") Integer patientId,
                                            @Param("p_FromUtc") LocalDateTime fromUtc,
                                            @Param("p_ToUtc") LocalDateTime toUtc);

    List<AppointmentDto> listAppointmentsByDoctorV2(@Param("p_DoctorId") Integer doctorId,
                                                    @Param("p_FromUtc") LocalDateTime fromUtc,
                                                    @Param("p_ToUtc") LocalDateTime toUtc);

    List<AppointmentDto> listAppointmentsByPatientV2(@Param("p_PatientId") Integer patientId,
                                                     @Param("p_FromUtc") LocalDateTime fromUtc,
                                                     @Param("p_ToUtc") LocalDateTime toUtc);

    List<AppointmentDto> listAppointments(@Param("p_DoctorId") Integer doctorId,
                                          @Param("p_PatientId") Integer patientId,
                                          @Param("p_FromUtc") LocalDateTime fromUtc);

    List<AppointmentDto> listAppointmentsByDoctor(@Param("p_DoctorId") Integer doctorId,
                                                  @Param("p_FromUtc") LocalDateTime fromUtc);

    List<AppointmentDto> listAppointmentsByPatient(@Param("p_PatientId") Integer patientId,
                                                   @Param("p_FromUtc") LocalDateTime fromUtc);

    void cancelAppointmentByDoctor(@Param("apptId") Long apptId,
                                   @Param("doctorId") Integer doctorId,
                                   @Param("byUserId") Integer byUserId);

    void cancelAppointmentByAdmin(@Param("p_ApptId") Long apptId,
                                  @Param("p_AdminUserId") Integer adminUserId,
                                  @Param("p_Reason") String reason);
}
