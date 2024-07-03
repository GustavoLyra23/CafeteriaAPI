package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.entities.Client;
import com.example.projetoLanchonete.demo.repository.ClientRepository;
import com.example.projetoLanchonete.demo.service.exception.ObjectAlreadyExistsException;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public Client save(Client client) {

        Optional<Client> cpfTest = clientRepository.findById(client.getCpf());
        Client client1 = cpfTest.get();

        if (client.getCpf().equals(client1.getCpf())) {
            throw new ObjectAlreadyExistsException("Cpf already exists");
        }

        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(String id) {
        Optional<Client> user = clientRepository.findById(id);
        if (!user.isPresent()) {
            throw new ObjectNotFoundException("User not found with id " + id);
        }
        return user.get();
    }


}
