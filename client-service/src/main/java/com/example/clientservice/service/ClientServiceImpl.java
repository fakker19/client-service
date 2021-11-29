package com.example.clientservice.service;

import com.example.clientservice.domain.model.Client;
import com.example.clientservice.domain.model.Patient;
import com.example.clientservice.domain.repository.ClientRepository;
import com.example.clientservice.domain.repository.PatientRepository;
import com.example.clientservice.domain.service.ClientService;
import com.example.clientservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Override
    public List<Client> getAllClients() {
        return (ArrayList<Client>) clientRepository.findAll();
    }

    @Override
    public Client updateClient(Long clientId, Client client) {
        return clientRepository.findById(clientId).map(
                Client -> {
                    Client.setName(client.getName());
                    return clientRepository.save(Client);
                }
        ).orElseThrow(() -> new ResourceNotFoundException("Client", "Id", clientId));
    }

    @Override
    public ResponseEntity<?> deleteClient(Long clientId) {
        Client existed = clientRepository.findById(clientId)
                .orElseThrow(()-> new ResourceNotFoundException("Client", "Id", clientId));

        List<Patient> patientList = patientRepository.findAllByClientId(clientId);

        if (patientList!=null){
            for(Patient patient: patientList){
                Long patientId = patient.getId();
                patientRepository.delete(patient);
            }
        }

        clientRepository.delete(existed);
        return ResponseEntity.ok().build();
    }
}
