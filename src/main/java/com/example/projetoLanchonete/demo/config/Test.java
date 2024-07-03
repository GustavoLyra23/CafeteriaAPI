package com.example.projetoLanchonete.demo.config;

import com.example.projetoLanchonete.demo.entities.Client;
import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.entities.Product;
import com.example.projetoLanchonete.demo.entities.enums.Category;
import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.example.projetoLanchonete.demo.repository.ClientRepository;
import com.example.projetoLanchonete.demo.repository.OrderRepository;
import com.example.projetoLanchonete.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Arrays;

@Configuration
public class Test implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void run(String... args) throws Exception {
        clientRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();


        Client c1 = new Client("19764514774", "Gustavo", "gustavolyra@gmail.com");
        clientRepository.save(c1);
        Order order1 = new Order(null, Status.RECEBIDO, Instant.now());
        Product product1 = new Product(null, 20.0, Category.LANCHE);
        Product product2 = new Product(null, 30.0, Category.LANCHE);
        order1.getProducts().addAll(Arrays.asList(product1, product2));
        c1.getOrders().add(order1);
        order1.getClients().add(c1);

        productRepository.save(product1);
        orderRepository.save(order1);
        clientRepository.save(c1);

        System.out.println(order1.getOrderPrice());
    }
}
