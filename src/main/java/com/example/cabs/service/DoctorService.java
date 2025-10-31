package com.example.cabs.service;

import com.example.cabs.dto.AdminCreateDoctorRequest;
import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.dto.DoctorUpdateRequest;

import java.time.LocalDate;
import java.util.List;

public interface DoctorService {
    List<DoctorBasicDto> listDoctorsBasic();
    void createDoctor(String email);
    void createDoctor(AdminCreateDoctorRequest request);
    void updateDoctor(DoctorUpdateRequest request);
    void deleteDoctorSoft(Integer doctorId);
    Integer getDoctorIdByUserId(Integer userId);
    void adminGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer adminUserId);
    void adminGenerateSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate, Integer startHour, Integer endHour, Integer adminUserId);
    void doctorGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId);
    void doctorGenerateSlotsByUser(Integer doctorUserId, LocalDate workDate, Integer startHour, Integer endHour, Integer byUserId);
    DoctorUpdateRequest getMyProfile(Integer userId);
}
