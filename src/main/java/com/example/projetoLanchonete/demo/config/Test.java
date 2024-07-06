package com.example.projetoLanchonete.demo.config;

import com.example.projetoLanchonete.demo.entities.Client;
import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.entities.Product;
import com.example.projetoLanchonete.demo.entities.enums.Category;
import com.example.projetoLanchonete.demo.repository.ClientRepository;
import com.example.projetoLanchonete.demo.repository.OrderRepository;
import com.example.projetoLanchonete.demo.repository.ProductRepository;
import com.example.projetoLanchonete.demo.service.PaymentService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Test implements CommandLineRunner {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    PaymentService paymentService;

    @Override
    public void run(String... args) throws Exception {
        clientRepository.deleteAll();
        productRepository.deleteAll();
        orderRepository.deleteAll();
//        String orderId, Status status, Instant orderDate, Client clients, String descricao
        Order newOrder = new Order("1", com.example.projetoLanchonete.demo.entities.enums.Status.RECEBIDO,
                new Client("19764514774", "Gustavo", "gustavolyra23@gmail.com"));

        Product newProduct = new Product("1", 20.0, Category.LANCHE, "Hamburger");
        Product newProduct2 = new Product("2", 30.0, Category.BEBIDA, "Coca-Cola");

        productRepository.saveAll(Arrays.asList(newProduct, newProduct2, newProduct));

        newOrder.getProducts().addAll(Arrays.asList(newProduct, newProduct2));
        newOrder.setOrderPrice();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(newOrder));


    }
}


