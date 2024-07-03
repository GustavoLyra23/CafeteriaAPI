package com.example.projetoLanchonete.demo.entities;


import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(value = "orderDoc")
public class Order implements Serializable {

    @Id
    private String orderId;
    @DBRef
    @JsonIgnore
    private List<Client> clients = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private Status status;
    private Instant orderDate;

    public Order() {
    }

    public Order(String orderId, Status status, Instant orderDate) {
        this.orderId = orderId;
        this.status = status;
        this.orderDate = orderDate;
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderId);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getOrderPrice() {
        return products.stream()
                .map(a -> a.getPrice())
                .reduce(0.0, Double::sum);
    }
}
