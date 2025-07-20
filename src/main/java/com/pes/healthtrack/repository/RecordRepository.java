package com.pes.healthtrack.repository;

import com.pes.healthtrack.model.Patient;
import com.pes.healthtrack.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findByPatientOrderByRecordDateDesc(Patient patient);
}