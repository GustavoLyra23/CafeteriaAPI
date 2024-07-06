package com.example.projetoLanchonete.demo.entities;


import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(value = "orderDoc")
public class Order implements Serializable {

    @Id
    private String orderId;
    private Client client;
    private List<Product> products = new ArrayList<>();
    private Status status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String paymentLink;
    private Double orderPrice;
    private String paymentStatus;


    public Order() {
    }

    public Order(String orderId, Status status, Client clients) {
        this.orderId = orderId;
        this.status = status;
        this.client = clients;

    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Client getClient() {
        return client;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
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

    public void setOrderPrice() {
        this.orderPrice = getOrderPrice();
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPaymentLink() {
        return paymentLink;
    }

    public void setPaymentLink(String paymentLink) {
        this.paymentLink = paymentLink;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
