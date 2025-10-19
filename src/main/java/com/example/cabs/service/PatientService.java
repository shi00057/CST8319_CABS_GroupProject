package com.example.cabs.service;

import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.dto.PatientUpdateRequest;

import java.util.List;

public interface PatientService {
    void registerPatient(String email);
    Patient getPatientById(Integer patientId);
    Integer getPatientIdByUserId(Integer userId);
    List<PatientActivationDto> listPatientsPendingActivation();
    void activateUser(Integer userId, Boolean isActive);
    void updatePatient(PatientUpdateRequest request);
    void deletePatientSoft(Integer patientId);
}
