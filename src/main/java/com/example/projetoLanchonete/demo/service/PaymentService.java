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

@Service
public class PaymentService {

    @Value("${mercadopago.access_token}")
    private String accessToken;

    public String createPaymentLink(Double amount) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);

        PreferenceClient client = new PreferenceClient();

        PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                .title("FoodService")
                .quantity(1)
                .unitPrice(BigDecimal.valueOf(amount.floatValue()))
                .build();

        PreferenceRequest request = PreferenceRequest.builder()
                .items(Collections.singletonList(itemRequest))
                .build();

        Preference preference = client.create(request);
        return preference.getInitPoint() + ";" + preference.getId();
    }

    public String getPaymentStatus(String paymentId) throws Exception {
        MercadoPagoConfig.setAccessToken(accessToken);
        PaymentClient client = new PaymentClient();
        Payment payment = client.get(Long.valueOf(paymentId));
        return payment.getStatus();

    }


}
