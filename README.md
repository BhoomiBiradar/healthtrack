# HealthTrack – Hospital Management System

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/SpringBoot-3.5.3-brightgreen)
![MySQL](https://img.shields.io/badge/MySQL-8.x-orange)

**HealthTrack** is a web-based hospital management system built using **Spring Boot** and **MySQL**. It streamlines appointment booking, medical record management, and offers secure role-based access for doctors and patients.

---

## High-Level Design (HLD)

<div align="center">
  <img src="diagrams/HLD.png" alt="HLD Diagram" width="300"/>
</div>

---

## Low-Level Design (LLD)

<div align="center">
  <img src="diagrams/LLD.png" alt="LLD Diagram" width="300"/>
</div>

---

## Features

###  Role-Based Access
- **Patients can**:
  - Register & log in securely
  - Book appointments with doctors
  - View appointment history and medical records
  - Update profile info
- **Doctors can**:
  - View & manage appointments
  - Add/edit patient medical records
  - Access patient list and histories

### Technical Highlights
- Secure authentication with **Spring Security** + **BCrypt**
- Follows **MVC architecture** (Controller → Service → Repository)
- Database integration using **Spring Data JPA**
- Dynamic web pages via **Thymeleaf**
- Responsive UI using **Bootstrap**


---

## Tech Stack

| Category    | Technology                             |
|-------------|-----------------------------------------|
| Backend     | Java 17, Spring Boot 3.5.3              |
| Security    | Spring Security 6.x, BCrypt             |
| Database    | MySQL 8.x                               |
| Frontend    | Thymeleaf, HTML5, CSS3, Bootstrap 4.5   |
| Build Tool  | Maven                                   |

---

##  Getting Started

### Prerequisites
- Java 17+
- MySQL Server
- Maven
- IDE (IntelliJ / VS Code)

### 🛠️ Setup Instructions

#### Create the MySQL Database
#### Configure application.properties
#### Build and Run the Project
- mvn clean install
- mvn spring-boot:run
- Visit the app at: http://localhost:8080

## 🖥️ UI Screenshots

### 🔐 Login Page
<div align="center">
  <img src="demo/login.png" alt="Login Page"/>
</div>

---

### 👤 Patient Dashboard
<div align="center">
  <img src="demo/patient.png" alt="Patient Dashboard"/>
</div>

---

### 👨‍⚕️ Doctor Dashboard
<div align="center">
  <img src="demo/doctor.png" alt="Doctor Dashboard"/>
</div>

---

### 📋 Patient List View
<div align="center">
  <img src="demo/patient_list.png" alt="Patient List"/>
</div>

