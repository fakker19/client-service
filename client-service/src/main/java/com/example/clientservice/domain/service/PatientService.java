package com.example.clientservice.domain.service;

import com.example.clientservice.domain.model.Patient;
import org.springframework.http.ResponseEntity;

import java.net.ResponseCache;
import java.util.List;
import java.util.Optional;

public interface PatientService {
    Patient createPatientByClientId(Long clientId, Patient patient);
    Optional<Patient> getPatientByIdAndClientId(Long patientId, Long clientId);
    List<Patient> getAllPatientsByClientId(Long clientId);
    Patient updatePatient(Long clientId, Long patientId, Patient patient);
    ResponseEntity<?> deletePatient(Long clientId, Long patientId);
}
