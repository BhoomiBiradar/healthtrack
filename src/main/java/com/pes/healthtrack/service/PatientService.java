package com.pes.healthtrack.service;

import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.model.User;
import com.pes.healthtrack.repository.PatientRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> findPatientByUser(User user) {
        return patientRepository.findByUser(user);
    }
}