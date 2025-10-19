package com.example.cabs.repository;

import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PatientMapper {

    Integer registerPatient(@Param("email") String email,
                            @Param("passwordHash") byte[] passwordHash,
                            @Param("salt") byte[] salt,
                            @Param("fullName") String fullName,
                            @Param("phone") String phone);

    Patient getPatientById(@Param("patientId") Integer patientId);

    Integer getPatientIdByUserId(@Param("userId") Integer userId);

    List<PatientActivationDto> listPatientsPendingActivation();

    int activateUser(@Param("userId") Integer userId,
                     @Param("isActive") Boolean isActive);

    int updatePatient(@Param("patientId") Integer patientId,
                      @Param("fullName") String fullName);

    int deletePatientSoft(@Param("patientId") Integer patientId);
}
