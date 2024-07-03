package com.example.projetoLanchonete.demo.resources;

import com.example.projetoLanchonete.demo.entities.Client;
import com.example.projetoLanchonete.demo.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "v1/client")
public class ClientResource {

    @Autowired
    private ClientService clientService;


    @GetMapping(value = "v1/{id}")
    public ResponseEntity<Client> getClient(@PathVariable String id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok().body(client);
    }

    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody Client client) {
        Client user = clientService.save(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getCpf()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAll();
        return ResponseEntity.ok().body(clients);
    }

}
