package com.example.projetoLanchonete.demo.repository;

import com.example.projetoLanchonete.demo.entities.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
}
