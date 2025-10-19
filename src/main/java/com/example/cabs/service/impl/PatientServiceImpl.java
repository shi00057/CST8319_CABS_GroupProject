package com.example.cabs.service.impl;

import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.dto.PatientUpdateRequest;
import com.example.cabs.repository.impl.PatientRepositoryImpl;
import com.example.cabs.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepositoryImpl repo;

    public PatientServiceImpl(PatientRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    @Transactional
    public void registerPatient(String email) {
        repo.registerPatient(email);
    }

    @Override
    public Patient getPatientById(Integer patientId) {
        return repo.getPatientById(patientId);
    }

    @Override
    public Integer getPatientIdByUserId(Integer userId) {
        return repo.getPatientIdByUserId(userId);
    }

    @Override
    public List<PatientActivationDto> listPatientsPendingActivation() {
        return repo.listPatientsPendingActivation();
    }

    @Override
    @Transactional
    public void activateUser(Integer userId, Boolean isActive) {
        repo.activateUser(userId, isActive);
    }

    @Override
    @Transactional
    public void updatePatient(PatientUpdateRequest request) {
        repo.updatePatient(request.getPatientId(), request.getFullName());
    }

    @Override
    @Transactional
    public void deletePatientSoft(Integer patientId) {
        repo.deletePatientSoft(patientId);
    }
}
