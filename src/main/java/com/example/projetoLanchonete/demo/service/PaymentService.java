package com.example.projetoLanchonete.demo.service;

import com.example.projetoLanchonete.demo.service.exception.PaymentException;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    @Value("${mercadopago.access_token}")
    private String accessToken;


    public String createPaymentLink(Double amount, String id) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);

        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title("FoodService")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(amount.floatValue()))
                .build();

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("order_id", id);

        PreferenceRequest request = PreferenceRequest.builder()
                .items(Collections.singletonList(itemRequest))
                .notificationUrl("https://6e52-187-61-91-165.ngrok-free.app/v1/webhook")
                .metadata(metadata)
                .build();

        Preference preference = client.create(request);
        return preference.getInitPoint();
    }

    public String getPaymentStatus(Long paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);
        return payment.getStatus();
    }

    public String getOrderIdFromPayment(String paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(Long.parseLong(paymentId));

        Map<String, Object> metadata = payment.getMetadata();
        return metadata != null ? (String) metadata.get("order_id") : null;
    }

    public String handleWebHook(Map<String, Object> payload) {
        try {
            if (payload != null && payload.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) payload.get("data");
                if (data != null && data.containsKey("id")) {
                    String paymentId = data.get("id").toString();
                    String orderId = getOrderIdFromPayment(paymentId);
                    System.out.println("Order ID: " + orderId);
                    System.out.println(paymentId);
                    return orderId + ";" + paymentId;
                } else {
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
