package com.example.cabs.repository;

import com.example.cabs.dto.DoctorBasicDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface DoctorAdminMapper {
    List<DoctorBasicDto> listDoctorsBasic();
    Map<String, Object> createDoctor(@Param("p_Email") String email,
                                     @Param("p_PasswordHash") byte[] passwordHash,
                                     @Param("p_Salt") byte[] salt,
                                     @Param("p_Name") String name,
                                     @Param("p_Specialty") String specialty,
                                     @Param("p_Phone") String phone,
                                     @Param("p_IsActive") boolean isActive);
    int updateDoctor(@Param("p_DoctorId") Integer doctorId, @Param("p_Name") String name);
    int deleteDoctorSoft(@Param("p_DoctorId") Integer doctorId);
    Integer getDoctorIdByUserId(@Param("p_UserId") Integer userId);
    int adminGenerateSlots(@Param("p_DoctorId") Integer doctorId,
                           @Param("p_WorkDate") LocalDate workDate,
                           @Param("p_StartHour") Integer startHour,
                           @Param("p_EndHour") Integer endHour,
                           @Param("p_AdminUserId") Integer adminUserId);
    int adminGenerateSlotsRange(@Param("p_DoctorId") Integer doctorId,
                                @Param("p_FromDate") LocalDate fromDate,
                                @Param("p_ToDate") LocalDate toDate,
                                @Param("p_StartHour") Integer startHour,
                                @Param("p_EndHour") Integer endHour,
                                @Param("p_AdminUserId") Integer adminUserId);
}
