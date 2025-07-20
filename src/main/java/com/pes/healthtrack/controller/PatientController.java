package com.pes.healthtrack.controller;

import com.pes.healthtrack.model.Appointment; 
import com.pes.healthtrack.model.Patient;    
import com.pes.healthtrack.model.Record;     
import com.pes.healthtrack.model.User;      
import com.pes.healthtrack.model.Doctor;     

import com.pes.healthtrack.service.AppointmentService; 
import com.pes.healthtrack.service.DoctorService;      
import com.pes.healthtrack.service.PatientService;     
import com.pes.healthtrack.service.RecordService;      

import com.pes.healthtrack.repository.UserRepository;  

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;
    private final RecordService recordService;
    private final UserRepository userRepository;
    private final DoctorService doctorService; 


    public PatientController(PatientService patientService, AppointmentService appointmentService,
                             RecordService recordService, UserRepository userRepository,
                             DoctorService doctorService) { 
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.recordService = recordService;
        this.userRepository = userRepository;
        this.doctorService = doctorService; 
    }

    private Patient getCurrentPatient() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); 
        User currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("Logged in user not found!"));
        return patientService.findPatientByUser(currentUser)
                .orElseThrow(() -> new RuntimeException("Patient profile not found for logged in user."));
    }

    @GetMapping("/dashboard")
    public String patientDashboard(Model model) {
        Patient patient = getCurrentPatient();
        model.addAttribute("patient", patient);
        model.addAttribute("upcomingAppointments", appointmentService.getAppointmentsByPatient(patient));
        model.addAttribute("medicalRecords", recordService.getRecordsByPatient(patient));
        return "patient-dashboard";
    }

    @GetMapping("/book-appointment")
    public String showBookAppointmentForm(Model model) {
        List<User> doctorUsers = userRepository.findByRole(User.Role.DOCTOR);
        model.addAttribute("doctorUsers", doctorUsers);
        return "book-appointment";
    }

    @PostMapping("/book-appointment")
    public String bookAppointment(@RequestParam Long doctorUserId, 
                                  @RequestParam LocalDate appointmentDate,
                                  @RequestParam LocalTime appointmentTime,
                                  @RequestParam String reason,
                                  Model model) {
        try {
            User doctorUser = userRepository.findById(doctorUserId)
                    .orElseThrow(() -> new IllegalArgumentException("Doctor User not found with ID: " + doctorUserId));

            Doctor doctor = doctorService.findDoctorByUser(doctorUser)
                .orElseThrow(() -> new IllegalArgumentException("Doctor profile not found for selected user."));

            Patient patient = getCurrentPatient();
            
            appointmentService.bookAppointment(patient.getId(), doctor.getId(), appointmentDate, appointmentTime, reason);
            return "redirect:/patient/dashboard?bookingSuccess";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("doctorUsers", userRepository.findByRole(User.Role.DOCTOR));
            return "book-appointment";
        }
    }

    @GetMapping("/profile")
    public String viewPatientProfile(Model model) {
        Patient patient = getCurrentPatient();
        model.addAttribute("patient", patient);
        return "patient-profile";
    }

    @PostMapping("/profile/update")
    public String updatePatientProfile(@RequestParam String gender,
                                       @RequestParam int age,
                                       @RequestParam String contactNumber,
                                       @RequestParam String address,
                                       @RequestParam String bloodGroup,
                                       @RequestParam String allergies,
                                       @RequestParam String medicalHistory) {
        Patient patient = getCurrentPatient();
        patient.setGender(gender);
        patient.setAge(age);
        patient.setContactNumber(contactNumber);
        patient.setAddress(address);
        patient.setBloodGroup(bloodGroup);
        patient.setAllergies(allergies);
        patient.setMedicalHistory(medicalHistory);
        patientService.savePatient(patient);
        return "redirect:/patient/dashboard?profileUpdated";
    }
}