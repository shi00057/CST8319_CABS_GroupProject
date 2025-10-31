package com.example.cabs.service.impl;

import com.example.cabs.dto.AppointmentDto;
import com.example.cabs.dto.SlotDto;
import com.example.cabs.repository.impl.AppointmentRepositoryImpl;
import com.example.cabs.repository.impl.PatientRepositoryImpl;
import com.example.cabs.repository.impl.SlotRepositoryImpl;
import com.example.cabs.service.AppointmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
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
        List<AppointmentDto> items;
        if (doctorId != null && patientId == null) {
            items = apptRepo.listAppointmentsByDoctor(doctorId, fromUtc);
        } else if (patientId != null && doctorId == null) {
            items = apptRepo.listAppointmentsByPatient(patientId, fromUtc);
        } else {
            items = apptRepo.listAppointments(doctorId, patientId, fromUtc);
        }
        markCancellable(items);
        return items;
    }

    @Override
    public List<AppointmentDto> listAppointmentsByDoctor(Integer doctorId, LocalDateTime fromUtc) {
        List<AppointmentDto> items = apptRepo.listAppointmentsByDoctor(doctorId, fromUtc);
        markCancellable(items);
        return items;
    }

    @Override
    public List<AppointmentDto> listAppointmentsByPatient(Integer patientId, LocalDateTime fromUtc) {
        List<AppointmentDto> items = apptRepo.listAppointmentsByPatient(patientId, fromUtc);
        markCancellable(items);
        return items;
    }

    @Override
    @Transactional
    public void cancelAppointmentByAdmin(Long apptId, Integer adminUserId, String reason) {
        apptRepo.cancelAppointmentByAdmin(apptId, adminUserId, reason);
    }

    private void markCancellable(List<AppointmentDto> items) {
        LocalDateTime now = LocalDateTime.now(Clock.systemUTC());
        for (AppointmentDto a : items) {
            boolean can = a != null
                    && a.getStartUtc() != null
                    && a.getStatus() != null
                    && "Booked".equalsIgnoreCase(a.getStatus())
                    && a.getStartUtc().isAfter(now);
            a.setCancellable(can);
        }
    }
}
