package com.example.projetoLanchonete.demo.repository;

import com.example.projetoLanchonete.demo.entities.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
