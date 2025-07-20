package com.pes.healthtrack.service;

import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.model.Record;
import com.pes.healthtrack.repository.RecordRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecordService {
    private final RecordRepository recordRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public RecordService(RecordRepository recordRepository, PatientService patientService, DoctorService doctorService) {
        this.recordRepository = recordRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public List<Record> getAllRecords() {
        return recordRepository.findAll();
    }

    public Optional<Record> getRecordById(Long id) {
        return recordRepository.findById(id);
    }

    public Record saveRecord(Record record) {
        return recordRepository.save(record);
    }

    public Record createMedicalRecord(Long patientId, Long doctorId, String notes, String prescription) {
        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorService.getDoctorById(doctorId)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        Record record = new Record();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setNotes(notes);
        record.setPrescription(prescription);
        record.setRecordDate(LocalDateTime.now());
        return recordRepository.save(record);
    }

    public Record updateMedicalRecord(Long recordId, String notes, String prescription) {
        Record record = recordRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Medical record not found with ID: " + recordId));
        record.setNotes(notes);
        record.setPrescription(prescription);
        return recordRepository.save(record);
    }

    public List<Record> getRecordsByPatient(Patient patient) {
        return recordRepository.findByPatientOrderByRecordDateDesc(patient);
    }
}