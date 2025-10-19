package com.example.cabs.repository.impl;

import com.example.cabs.dto.DoctorBasicDto;
import com.example.cabs.repository.DoctorAdminMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DoctorAdminRepositoryImpl {
    private final DoctorAdminMapper mapper;

    public DoctorAdminRepositoryImpl(DoctorAdminMapper mapper) {
        this.mapper = mapper;
    }

    public List<DoctorBasicDto> listDoctorsBasic() {
        return mapper.listDoctorsBasic();
    }

    public void createDoctor(String email) {
        mapper.createDoctor(email);
    }

    public void updateDoctor(Integer doctorId, String name) {
        mapper.updateDoctor(doctorId, name);
    }

    public void deleteDoctorSoft(Integer doctorId) {
        mapper.deleteDoctorSoft(doctorId);
    }

    public Integer getDoctorIdByUserId(Integer userId) {
        return mapper.getDoctorIdByUserId(userId);
    }

    public void adminGenerateSlots(Integer doctorId, LocalDate workDate, Integer startHour, Integer endHour, Integer adminUserId) {
        mapper.adminGenerateSlots(doctorId, workDate, startHour, endHour, adminUserId);
    }

    public void adminGenerateSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate, Integer startHour, Integer endHour, Integer adminUserId) {
        mapper.adminGenerateSlotsRange(doctorId, fromDate, toDate, startHour, endHour, adminUserId);
    }
}
