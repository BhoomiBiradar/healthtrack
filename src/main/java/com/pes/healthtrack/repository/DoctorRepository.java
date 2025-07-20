package com.pes.healthtrack.repository;

import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUser(User user);
}