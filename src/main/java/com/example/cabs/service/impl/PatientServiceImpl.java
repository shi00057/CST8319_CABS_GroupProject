package com.example.cabs.service.impl;

import com.example.cabs.common.util.PasswordCrypto;
import com.example.cabs.domain.Patient;
import com.example.cabs.dto.PatientActivationDto;
import com.example.cabs.dto.PatientRegisterRequest;
import com.example.cabs.dto.PatientUpdateRequest;
import com.example.cabs.repository.impl.PatientRepositoryImpl;
import com.example.cabs.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepositoryImpl repo;

    public PatientServiceImpl(PatientRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public void registerPatient(PatientRegisterRequest req) {
        if (!Objects.equals(req.getPassword(), req.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        byte[] salt = PasswordCrypto.generateSalt(16); // match DB RANDOM_BYTES(16)
        byte[] hash = PasswordCrypto.mysqlHash(salt, req.getPassword());
        repo.registerPatient(req.getEmail(), hash, salt, req.getFullName(), req.getPhone());
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
