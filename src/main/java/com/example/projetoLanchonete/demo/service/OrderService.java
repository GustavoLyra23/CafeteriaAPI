package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.repository.OrderRepository;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order insert(Order order) {
        return orderRepository.save(order);
    }


    public Order findById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent()) {
            throw new ObjectNotFoundException("Order not found");
        }
        return order.get();
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
