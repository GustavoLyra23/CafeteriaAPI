package com.example.projetoLanchonete.demo.resources;

import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "v1/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);

    }



}
