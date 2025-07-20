package com.pes.healthtrack.service;

import com.pes.healthtrack.model.Appointment;
import com.pes.healthtrack.model.Doctor;
import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.repository.AppointmentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientService patientService,
                              DoctorService doctorService) {
        this.appointmentRepository = appointmentRepository;
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment bookAppointment(Long patientId, Long doctorId, LocalDate date, LocalTime time, String reason) {
        Patient patient = patientService.getPatientById(patientId)
                            .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorService.getDoctorById(doctorId)
                          .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctorId));

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setAppointmentDate(date);
        appointment.setAppointmentTime(time);
        appointment.setReason(reason);
        appointment.setStatus(Appointment.Status.PENDING);

        return appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Patient patient) {
        return appointmentRepository.findByPatientOrderByAppointmentDateAscAppointmentTimeAsc(patient);
    }

    public List<Appointment> getAppointmentsByDoctor(Doctor doctor) {
        return appointmentRepository.findByDoctorOrderByAppointmentDateAscAppointmentTimeAsc(doctor);
    }

    public Appointment updateAppointmentStatus(Long appointmentId, Appointment.Status newStatus) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));
        appointment.setStatus(newStatus);
        return appointmentRepository.save(appointment);
    }
}