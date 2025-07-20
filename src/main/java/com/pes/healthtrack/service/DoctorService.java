package com.pes.healthtrack.service;

import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.User;
import com.pes.healthtrack.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> findDoctorByUser(User user) {
        return doctorRepository.findByUser(user);
    }
}