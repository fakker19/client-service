package com.example.clientservice.domain.repository;

import com.example.clientservice.domain.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findAllByClientId(Long clientId);
    Optional<Patient> findByIdAndClientId(Long clientId, Long patientId);
}
