package com.example.projetoLanchonete.demo.resources;

import com.example.projetoLanchonete.demo.entities.Order;
import com.example.projetoLanchonete.demo.entities.enums.Status;
import com.example.projetoLanchonete.demo.service.OrderService;
import com.example.projetoLanchonete.demo.service.PaymentService;
import com.example.projetoLanchonete.demo.service.exception.PaymentException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("v1/webhook")
public class WebhookResource {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) throws Exception {
        try {
            String[] paymentData = paymentService.handleWebHook(payload).split(";");
            var paymentStatus = paymentService.getPaymentStatus(Long.valueOf(paymentData[1]));
            if (paymentStatus.equals("approved")) {
                Order order = orderService.findById(paymentData[0]);
                order.setPaymentStatus("approved");
                order.setStatus(Status.EM_PREPARACAO);
                orderService.update(order);
            }
        } catch (NullPointerException e) {
            throw new PaymentException(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

}
