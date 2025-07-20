
INSERT IGNORE INTO users (id, email, password, name, role) VALUES
(1, 'patient1@example.com', '$2a$10$wE0Ym5oH.gK5.3/qQJ3.eO.aZ/1yQ.aJz.aF.qE.m.Q.x.S.x.a.x.x.x', 'Alice Smith', 'PATIENT'),
(2, 'doctor1@example.com', '$2a$10$wE0Ym5oH.gK5.3/qQJ3.eO.aZ/1yQ.aJz.aF.qE.m.Q.x.S.x.a.x.x.x', 'Dr. Bob Johnson', 'DOCTOR');

-- Insert Patients (linking to users)
INSERT IGNORE INTO patients (id, user_id, age, gender, contact_number, address, blood_group, allergies, medical_history) VALUES
(1, 1, 30, 'Female', '123-456-7890', '123 Main St, Anytown', 'O+', 'Pollen', 'No significant medical history. Regular checkups.');

-- Insert Doctors (linking to users)
INSERT IGNORE INTO doctors (id, user_id, specialization, contact_number, license_number) VALUES
(1, 2, 'General Physician', '098-765-4321', 'MD12345');

-- Insert Appointments (linking patient and doctor)
INSERT IGNORE INTO appointments (id, patient_id, doctor_id, appointment_date, appointment_time, reason, status) VALUES
(1, 1, 1, '2025-07-25', '10:00:00', 'Routine Checkup', 'PENDING'),
(2, 1, 1, '2025-08-10', '14:30:00', 'Follow-up on cold symptoms', 'CONFIRMED');

-- Insert Medical Records (linking patient and doctor)
INSERT IGNORE INTO records (id, patient_id, doctor_id, notes, prescription, record_date) VALUES
(1, 1, 1, 'Patient presented with mild cold symptoms. Advised rest and fluids.', 'Prescribed Paracetamol 500mg, 3 times a day for 3 days.', '2025-07-20 11:00:00'),
(2, 1, 1, 'Follow-up: Patient recovering well. No further symptoms.', 'Continue rest for another day.', '2025-07-22 09:00:00');