package com.pes.healthtrack.repository;

import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUser(User user);
}