package com.example.clientservice.controller;

import com.example.clientservice.domain.model.Patient;
import com.example.clientservice.domain.service.PatientService;
import com.example.clientservice.resource.PatientResource;
import com.example.clientservice.resource.SavePatientResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class PatientsController {
    @Autowired
    private PatientService patientService;
    private ModelMapper mapper;
    @GetMapping("/{clientId}/patients")
    public List<PatientResource> getAllPatientsByClientId(@PathVariable Long clientId){
        return patientService.getAllPatientsByClientId(clientId).stream().map(this::convertToResource).collect(Collectors.toList());
    }

    @GetMapping("/{clientId}/patients/{patientId}")
    public PatientResource getPatientByIdAndClientId(@PathVariable Long clientId, @PathVariable Long patientId){
        Optional<Patient> patient = patientService.getPatientByIdAndClientId(patientId, clientId);
        return patient.map(this::convertToResource).orElse(null);
    }

    @PostMapping("/{clientId}/patients")
    public PatientResource createPatient(@PathVariable Long clientId, @RequestBody SavePatientResource patientResource){
        Patient patient = convertToEntity(patientResource);
        return convertToResource(patientService.createPatientByClientId(clientId, patient));
    }

    @PutMapping("/{clientId}/patients/{patientId}")
    public PatientResource updateDistrict(@PathVariable Long clientId, @PathVariable Long patienId, @RequestBody SavePatientResource patientResource){
        Patient patient = convertToEntity(patientResource);
        return convertToResource(patientService.updatePatient(clientId, patienId,patient));
    }

    @DeleteMapping("/{clientId}/patients/{patientId}")
    public ResponseEntity<?> deletePatient(@PathVariable Long clientId, @PathVariable Long patientId){
        return patientService.deletePatient(clientId, patientId);
    }

    private Patient convertToEntity(SavePatientResource resource){
        return mapper.map(resource, Patient.class);
    }

    private PatientResource convertToResource(Patient entity){
        return mapper.map(entity, PatientResource.class);
    }
}
