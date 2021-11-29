package com.example.clientservice.controller;

import com.example.clientservice.domain.model.Client;
import com.example.clientservice.domain.service.ClientService;
import com.example.clientservice.resource.ClientResource;
import com.example.clientservice.resource.SaveClientResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientsController {
    @Autowired
    private ClientService clientService;

    private ModelMapper mapper;

    @GetMapping("/clients")
    public List<ClientResource> getAllClients(){
        return clientService.getAllClients()
                .stream()
                .map(
                        client -> mapper.map(client, ClientResource.class)
                ).collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    public ClientResource getClientById(@PathVariable Long id){
        Optional<Client> client = clientService.getClientById(id);
        return client.map(this::convertToResource).orElse(null);
    }

    @PostMapping("/clients")
    public ClientResource saveClient(@Valid @RequestBody SaveClientResource resource){
        Client client = convertToEntity(resource);
        return convertToResource(clientService.createClient(client));
    }

    @PutMapping("/clients/{id}")
    public ClientResource updateClient(@PathVariable Long id, @RequestBody SaveClientResource resource){
        Client client = convertToEntity(resource);
        return convertToResource(clientService.updateClient(id, client));
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id){
        return clientService.deleteClient(id);
    }



    private Client convertToEntity(SaveClientResource resource){
        return mapper.map(resource, Client.class);
    }

    private ClientResource convertToResource(Client entity){
        return mapper.map(entity, ClientResource.class);
    }
}
