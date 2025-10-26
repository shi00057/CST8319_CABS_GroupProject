package com.example.cabs.service.impl;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.repository.impl.AppointmentRepositoryImpl;
import com.example.cabs.repository.impl.PatientRepositoryImpl;
import com.example.cabs.repository.impl.SlotRepositoryImpl;
import com.example.cabs.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final SlotRepositoryImpl slotRepo;
    private final AppointmentRepositoryImpl apptRepo;
    private final PatientRepositoryImpl patientRepo;

    public AppointmentServiceImpl(SlotRepositoryImpl slotRepo, AppointmentRepositoryImpl apptRepo, PatientRepositoryImpl patientRepo) {
        this.slotRepo = slotRepo;
        this.apptRepo = apptRepo;
        this.patientRepo = patientRepo;
    }

    @Override
    public List<SlotDto> listAvailableSlots(Integer doctorId, LocalDate workDate) {
        return slotRepo.listAvailableSlots(doctorId, workDate);
    }

    @Override
    @Transactional
    public void clearSlotsRange(Integer doctorId, LocalDate fromDate, LocalDate toDate) {
        slotRepo.clearSlotsRange(doctorId, fromDate, toDate);
    }

    @Override
    @Transactional
    public void bookAppointment(Integer doctorId, Integer patientId, LocalDateTime startUtc) {
        apptRepo.bookAppointment(doctorId, patientId, startUtc);
    }

//    @Override
//    @Transactional
//    public void bookAppointmentForPatientUser(Integer doctorId, Integer patientUserId, LocalDateTime startUtc) {
//        Integer patientId = patientRepo.getPatientIdByUserId(patientUserId);
//        apptRepo.bookAppointment(doctorId, patientId, startUtc);
//    }

    @Override
    @Transactional
    public void bookAppointmentForPatientUser(Integer doctorId, Integer patientUserId, LocalDateTime startUtc, LocalDateTime endUtc) {
        Integer patientId = patientRepo.getPatientIdByUserId(patientUserId);
        apptRepo.bookAppointmentV2(doctorId, patientId, startUtc, endUtc, patientUserId);
    }

    @Override
    @Transactional
    public void cancelAppointment(Long apptId, Integer patientId, Integer byUserId) {
        apptRepo.cancelAppointment(apptId, patientId, byUserId);
    }

    @Override
    @Transactional
    public void cancelAppointmentForPatientUser(Long apptId, Integer patientUserId, Integer byUserId) {
        Integer patientId = patientRepo.getPatientIdByUserId(patientUserId);
        apptRepo.cancelAppointment(apptId, patientId, byUserId);
    }
    @Override
    @Transactional
    public void cancelAppointmentForDoctor(Long apptId, Integer doctorId, Integer byUserId) {
        apptRepo.cancelAppointmentByDoctor(apptId, doctorId, byUserId);
    }


    @Override
    public List<AppointmentDto> listAppointments(Integer doctorId, Integer patientId, LocalDateTime fromUtc) {
        return apptRepo.listAppointments(doctorId, patientId, fromUtc);
    }

    @Override
    public List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc) {
        return apptRepo.listAppointmentsByDoctor(doctorId, fromUtc);
    }

    @Override
    public List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc) {
        return apptRepo.listAppointmentsByPatient(patientId, fromUtc);
    }
}
