package com.example.cabs.repository;

import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PatientMapper {
    int registerPatient(@Param("p_Email") String email);
    Patient getPatientById(@Param("p_PatientId") Integer patientId);
    Integer getPatientIdByUserId(@Param("p_UserId") Integer userId);
    List<PatientActivationDto> listPatientsPendingActivation();
    int activateUser(@Param("p_UserId") Integer userId, @Param("p_IsActive") Boolean isActive);
    int updatePatient(@Param("p_PatientId") Integer patientId, @Param("p_FullName") String fullName);
    int deletePatientSoft(@Param("p_PatientId") Integer patientId);
}
