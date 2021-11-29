package com.example.clientservice.domain.service;

import com.example.clientservice.domain.model.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client createClient(Client client);
    Optional<Client> getClientById(Long clientId);
    List<Client> getAllClients();
    Client updateClient(Long clientId,Client client);
    ResponseEntity<?> deleteClient(Long clientId);
}
