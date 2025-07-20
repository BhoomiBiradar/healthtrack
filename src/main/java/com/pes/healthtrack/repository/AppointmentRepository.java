package com.pes.healthtrack.repository;

import com.pes.healthtrack.model.Appointment;
import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(Patient patient);
    List<Appointment> findByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(Doctor doctor);
}