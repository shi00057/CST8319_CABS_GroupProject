package com.example.cabs.service.impl;

import com.example.cabs.dto.AdminCreateDoctorRequest;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.repository.impl.DoctorAdminRepositoryImpl;
import com.example.cabs.repository.impl.DoctorSelfRepositoryImpl;
import com.example.cabs.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.cabs.common.util.PasswordCrypto;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {
    private final DoctorAdminRepositoryImpl adminRepo;
    private final DoctorSelfRepositoryImpl selfRepo;

    public DoctorServiceImpl(DoctorAdminRepositoryImpl adminRepo, DoctorSelfRepositoryImpl selfRepo) {
        this.adminRepo = adminRepo;
        this.selfRepo = selfRepo;
    }

    @Override
    public List<DoctorBasicDto> listDoctorsBasic() {
        return adminRepo.listDoctorsBasic();
    }

    @Override
    @Transactional
    public void createDoctor(String email) {
        adminRepo.createDoctorEmailOnly(email);
    }

    @Override
    @Transactional
    public void createDoctor(AdminCreateDoctorRequest request) {

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required.");
        }
        byte[] salt = PasswordCrypto.generateSalt(16);
        byte[] hash = PasswordCrypto.mysqlHash(salt, request.getPassword());

        boolean active = request.getIsActive() == null ? true : request.getIsActive();
        adminRepo.createDoctorFull(
                request.getEmail().trim(),
                request.getFullName(),
                request.getSpecialty(),
                request.getPhone(),
                hash,
                salt,
                active
        );
    }


    @Override
    @Transactional
    public void updateDoctor(DoctorUpdateRequest request) {
        adminRepo.updateDoctor(request.getDoctorId(), request.getName());
    }

    @Override
    @Transactional
    public void deleteDoctorSoft(Integer doctorId) {
        adminRepo.deleteDoctorSoft(doctorId);
    }

    @Override
    public Integer getDoctorIdByUserId(Integer userId) {
        return adminRepo.getDoctorIdByUserId(userId);
    }

    @Override
    @Transactional
    public void adminGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer adminUserId) {
        adminRepo.adminGenerateSlots(doctorId, workDate, startHour, endHour, adminUserId);
    }

    @Override
    @Transactional
    public void adminGenerateSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate, Integer startHour, Integer endHour, Integer adminUserId) {
        adminRepo.adminGenerateSlotsRange(doctorId, fromDate, toDate, startHour, endHour, adminUserId);
    }

//    @Override
//    @Transactional
//    public void doctorGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
//        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId, "SELF");
//    }
//
//    @Override
//    @Transactional
//    public void doctorGenerateSlotsByUser(Integer doctorUserId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
//        Integer doctorId = adminRepo.getDoctorIdByUserId(doctorUserId);
//        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId, "SELF");
//    }
    @Override
    @Transactional
    public void doctorGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId);
    }

    @Override
    @Transactional
    public void doctorGenerateSlotsByUser(Integer doctorUserId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
        Integer doctorId = adminRepo.getDoctorIdByUserId(doctorUserId);
        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId);
    }

    @Override
    public DoctorUpdateRequest getMyProfile(Integer userId) {
        return selfRepo.getProfileByUserId(userId);
    }

    private static byte[] generateSalt(int size) {
        byte[] s = new byte[size];
        new SecureRandom().nextBytes(s);
        return s;
    }

    private static byte[] pbkdf2(String password, byte[] salt, int iterations, int keyLenBits) {
        try {
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLenBits);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            return skf.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
