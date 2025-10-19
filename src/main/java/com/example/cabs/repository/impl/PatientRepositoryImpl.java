package com.example.cabs.repository.impl;

import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.repository.PatientMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PatientRepositoryImpl {
    private final PatientMapper mapper;

    public PatientRepositoryImpl(PatientMapper mapper) {
        this.mapper = mapper;
    }

    public int registerPatient(String email) {
        return mapper.registerPatient(email);
    }

    public Patient getPatientById(Integer patientId) {
        return mapper.getPatientById(patientId);
    }

    public Integer getPatientIdByUserId(Integer userId) {
        return mapper.getPatientIdByUserId(userId);
    }

    public List<PatientActivationDto> listPatientsPendingActivation() {
        return mapper.listPatientsPendingActivation();
    }

    public int activateUser(Integer userId, Boolean isActive) {
        return mapper.activateUser(userId, isActive);
    }

    public int updatePatient(Integer patientId, String fullName) {
        return mapper.updatePatient(patientId, fullName);
    }

    public int deletePatientSoft(Integer patientId) {
        return mapper.deletePatientSoft(patientId);
    }
}
