package com.pes.healthtrack.service;

import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.model.User;
import com.pes.healthtrack.repository.DoctorRepository;
import com.pes.healthtrack.repository.PatientRepository;
import com.pes.healthtrack.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PatientRepository patientRepository,
                       DoctorRepository doctorRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserRepository getUserRepository() { 
        return userRepository;
    }

    @Transactional
    public User registerUser(String name, String email, String password, User.Role role) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + email);
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setRole(role);
        User savedUser = userRepository.save(newUser);

        if (role == User.Role.PATIENT) {
            Patient patient = new Patient();
            patient.setUser(savedUser);
            patient.setAge(0);
            patient.setGender("Prefer not to say");
            patient.setContactNumber("");
            patient.setAddress("");
            patient.setBloodGroup("");
            patient.setAllergies("");
            patient.setMedicalHistory("");
            patientRepository.save(patient);
        } else if (role == User.Role.DOCTOR) {
            Doctor doctor = new Doctor();
            doctor.setUser(savedUser);
            doctor.setSpecialization("General Practitioner");
            doctor.setContactNumber("");
            doctor.setLicenseNumber("");
            doctorRepository.save(doctor);
        }
        return savedUser;
    }
}