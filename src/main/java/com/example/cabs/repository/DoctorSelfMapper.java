package com.example.cabs.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.cabs.dto.DoctorUpdateRequest;

import java.time.LocalDate;

@Mapper
public interface DoctorSelfMapper {
    int doctorGenerateSlots(@Param("p_DoctorId") Integer doctorId,
                            @Param("p_WorkDate") LocalDate workDate,
                            @Param("p_StartHour") Integer startHour,
                            @Param("p_EndHour") Integer endHour,
                            @Param("p_ByUserId") Integer byUserId,
                            @Param("p_Source") String source);
    DoctorUpdateRequest getProfileByUserId(@Param("userId") Integer userId);
    void updateDoctorSelf(DoctorUpdateRequest request);
}
