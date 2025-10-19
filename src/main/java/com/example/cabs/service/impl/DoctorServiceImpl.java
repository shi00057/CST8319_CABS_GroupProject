package com.example.cabs.service.impl;

import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.DoctorUpdateRequest;
import com.example.cabs.repository.impl.DoctorAdminRepositoryImpl;
import com.example.cabs.repository.impl.DoctorSelfRepositoryImpl;
import com.example.cabs.service.DoctorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        adminRepo.createDoctor(email);
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

    @Override
    @Transactional
    public void doctorGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId, "SELF");
    }

    @Override
    @Transactional
    public void doctorGenerateSlotsByUser(Integer doctorUserId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId) {
        Integer doctorId = adminRepo.getDoctorIdByUserId(doctorUserId);
        selfRepo.doctorGenerateSlots(doctorId, workDate, startHour, endHour, byUserId, "SELF");
    }
}
