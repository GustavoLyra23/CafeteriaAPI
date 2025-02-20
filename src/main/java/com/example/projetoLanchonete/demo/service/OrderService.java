package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.entities.Client;
import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.entities.Product;
import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.example.projetoLanchonete.demo.repository.ClientRepository;
import com.example.projetoLanchonete.demo.repository.OrderRepository;
import com.example.projetoLanchonete.demo.repository.ProductRepository;
import com.example.projetoLanchonete.demo.service.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public void insert(Order order) {
        if (order.getClient() == null || order.getProducts().isEmpty()) {
            throw new ObjectNotFoundException("Client or products not found");
        }
        Client client = order.getClient();
        if (!clientRepository.existsById(client.getCpf())) {
            clientRepository.save(client);
        }
        for (Product product : order.getProducts()) {
            if (!productRepository.existsById(product.getId())) {
                productRepository.save(product);
            }
        }
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
        findById(order.getOrderId());
        orderRepository.save(order);
    }

    public void updateOrderStatus(String orderId, Status status) {
        Order order = findById(orderId);
        order.setStatus(status);
    }

    public String getOrderPaymentLink(String orderId) {
        Order order = findById(orderId);
        return order.getPaymentLink();
    }

}