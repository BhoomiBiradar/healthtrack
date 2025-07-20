package com.pes.healthtrack.controller;

import com.pes.healthtrack.model.Appointment; 
import com.pes.healthtrack.model.Doctor;     
import com.pes.healthtrack.model.Patient;   
import com.pes.healthtrack.model.Record;    
import com.pes.healthtrack.model.User;     

import com.pes.healthtrack.service.AppointmentService; 
import com.pes.healthtrack.service.DoctorService;      
import com.pes.healthtrack.service.PatientService;    
import com.pes.healthtrack.service.RecordService;     

import com.pes.healthtrack.repository.UserRepository;  

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate; 
import java.time.LocalTime; 
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final RecordService recordService;
    private final PatientService patientService; 
    private final UserRepository userRepository;


    public DoctorController(DoctorService doctorService, AppointmentService appointmentService,
                            RecordService recordService, PatientService patientService, 
                            UserRepository userRepository) {
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
        this.patientService = patientService; 
        this.userRepository = userRepository;
    }

    private Doctor getCurrentDoctor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("Logged in user not found!"));
        return doctorService.findDoctorByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Doctor profile not found for logged in user."));
    }

    @GetMapping("/dashboard")
    public String doctorDashboard(Model model) {
        Doctor doctor = getCurrentDoctor();
        model.addAttribute("doctor", doctor);
        model.addAttribute("upcomingAppointments", appointmentService.getAppointmentsByDoctor(doctor));
        return "doctor-dashboard";
    }

    @GetMapping("/appointments")
    public String viewDoctorAppointments(Model model) {
        Doctor doctor = getCurrentDoctor();
        model.addAttribute("appointments", appointmentService.getAppointmentsByDoctor(doctor));
        return "doctor-appointments";
    }

    @PostMapping("/appointments/update-status")
    public String updateAppointmentStatus(@RequestParam Long appointmentId,
                                          @RequestParam Appointment.Status status) {
        appointmentService.updateAppointmentStatus(appointmentId, status);
        return "redirect:/doctor/appointments?statusUpdated";
    }

    @GetMapping("/patients")
    public String viewPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "doctor-patients-list";
    }

    @GetMapping("/patient/{patientId}/records")
    public String viewPatientRecords(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        model.addAttribute("patient", patient);
        model.addAttribute("medicalRecords", recordService.getRecordsByPatient(patient));
        return "doctor-patient-records";
    }

    @GetMapping("/patient/{patientId}/records/add")
    public String showAddRecordForm(@PathVariable Long patientId, Model model) {
        Patient patient = patientService.getPatientById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));
        model.addAttribute("patient", patient);
        model.addAttribute("record", new Record());
        return "add-edit-record";
    }

    @PostMapping("/patient/{patientId}/records/add")
    public String addMedicalRecord(@PathVariable Long patientId,
                                   @RequestParam String notes,
                                   @RequestParam String prescription) {
        Doctor doctor = getCurrentDoctor();
        recordService.createMedicalRecord(patientId, doctor.getId(), notes, prescription);
        return "redirect:/doctor/patient/" + patientId + "/records?recordAdded";
    }

    @GetMapping("/record/{recordId}/edit")
    public String showEditRecordForm(@PathVariable Long recordId, Model model) {
        Record record = recordService.getRecordById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Record not found"));
        model.addAttribute("record", record);
        model.addAttribute("patient", record.getPatient());
        return "add-edit-record";
    }

    @PostMapping("/record/{recordId}/edit")
    public String editMedicalRecord(@PathVariable Long recordId,
                                    @RequestParam String notes,
                                    @RequestParam String prescription) {
        recordService.updateMedicalRecord(recordId, notes, prescription);
        Record updatedRecord = recordService.getRecordById(recordId).get();
        return "redirect:/doctor/patient/" + updatedRecord.getPatient().getId() + "/records?recordUpdated";
    }
}