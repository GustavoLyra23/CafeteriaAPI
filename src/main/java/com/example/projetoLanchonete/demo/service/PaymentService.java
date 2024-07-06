package com.example.projetoLanchonete.demo.service;

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
                .notificationUrl("https://6e52-187-61-91-165.ngrok-free.app/webhook")
                .metadata(metadata)
                .build();

        Preference preference = client.create(request);
        System.out.println("Preference ID: " + preference.getId());
        System.out.println("Metadata: " + preference.getMetadata());
        System.out.println("Client ID: " + preference.getClientId());
        System.out.println("Auto Return: " + preference.getAutoReturn());
        System.out.println("Additional Info: " + preference.getAdditionalInfo());
        System.out.println("Collector ID: " + preference.getCollectorId());
        System.out.println("Items: " + preference.getItems());
        System.out.println("Marketplace: " + preference.getMarketplace());
        System.out.println("Response Content: " + preference.getResponse().getContent());
        System.out.println("Response Headers: " + preference.getResponse().getHeaders());

        return preference.getInitPoint();
    }

    public String getPaymentStatus(Long paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(paymentId);
        return payment.getStatus();
    }
}
