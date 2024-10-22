package com.example.projetoLanchonete.demo.resources;

import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.example.projetoLanchonete.demo.service.OrderService;
import com.example.projetoLanchonete.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "v1/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        List<Order> orders = orderService.findAll();
        return ResponseEntity.ok().body(orders);

    }

    @PostMapping(value = "v1/create")
    public ResponseEntity<String> create(@RequestBody Order order) throws Exception {
        var paymentData = paymentService.createPaymentLink(order.getOrderPrice(), order.getOrderId());
        String paymentStatus = "WAITING_PAYMENT";
        order.setPaymentStatus(paymentStatus);
        order.setPaymentLink(paymentData);
        orderService.insert(order);
        return ResponseEntity.ok().body(paymentData);
    }

    @PutMapping(value = "v1/updateOrderStatus/{id}/status")
    public ResponseEntity<String> updateStatus(@PathVariable String id, @RequestParam Status status) throws Exception {
        orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok().build();
    }
}

