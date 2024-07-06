package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.repository.OrderRepository;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import com.example.projetoLanchonete.demo.service.exception.PaymentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public void insert(Order order) {
//     if (order.getClient() != null || order.getProducts().isEmpty()) {
//            throw new ObjectNotFoundException("Client not found");
//       }
        orderRepository.save(order);
    }


    public Order findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new ObjectNotFoundException("Order not found");
        }
        return order.get();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public void update(Order order) {
        orderRepository.save(order);
    }

}