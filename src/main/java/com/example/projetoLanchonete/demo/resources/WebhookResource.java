package com.example.projetoLanchonete.demo.resources;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WebhookResource {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    @PostMapping
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) {
        try {
            if (payload != null && payload.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                if (data != null && data.containsKey("id")) {
                    String paymentId = data.get("id").toString();
                    String orderId = getOrderIdFromPayment(paymentId);
                    System.out.println("Order ID: " + orderId);
                    System.out.println(paymentId);

                } else {
                    System.err.println("Webhook payload is missing 'id' field in 'data'.");
                }
            } else {
                System.err.println("Webhook payload is missing 'data' field.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    private String getOrderIdFromPayment(String paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(Long.parseLong(paymentId));

        Map<String, Object> metadata = payment.getMetadata();
        return metadata != null ? (String) metadata.get("order_id") : null;
    }
}
