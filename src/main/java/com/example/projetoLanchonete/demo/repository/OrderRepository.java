package com.example.projetoLanchonete.demo.repository;

import com.example.projetoLanchonete.demo.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
}
