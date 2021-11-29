package com.example.clientservice.service;

import com.example.clientservice.domain.model.Patient;
import com.example.clientservice.domain.repository.ClientRepository;
import com.example.clientservice.domain.repository.PatientRepository;
import com.example.clientservice.domain.service.PatientService;
import com.example.clientservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Patient createPatientByClientId(Long clientId, Patient patient) {
        return clientRepository.findById(clientId).map(
                client -> {
                    patient.setClient(client);
                    return patientRepository.save(patient);
                }
        )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Client", "Id", clientId
                ));
    }

    @Override
    public Optional<Patient> getPatientByIdAndClientId(Long patientId, Long clientId) {
        return patientRepository.findByIdAndClientId(clientId, patientId);
    }

    @Override
    public List<Patient> getAllPatientsByClientId(Long clientId) {
        return patientRepository.findAllByClientId(clientId);
    }

    @Override
    public Patient updatePatient(Long clientId, Long patientId, Patient patient) {
        return patientRepository.findById(patientId).map(
                patient1 -> {
                    patient1.setName(patient.getName());
                    return patientRepository.save(patient1);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
    }

    @Override
    public ResponseEntity<?> deletePatient(Long clientId, Long patientId) {
        return patientRepository.findById(patientId).map(
                patient -> {
                    patientRepository.delete(patient);
                    return ResponseEntity.ok().build();
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
    }
}
