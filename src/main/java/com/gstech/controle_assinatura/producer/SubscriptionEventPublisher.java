package com.gstech.controle_assinatura.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.entities.Subscription;
import com.gstech.controle_assinatura.enums.EventType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubscriptionEventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public void publisherSubscriptionCreatedEvent(Subscription subscription) {

        // cria o payload do evento
        Map<String, Object> event = new HashMap<>();
        event.put("id", subscription.getId());
        event.put("type", EventType.SUBSCRIPTION_CREATED);
        event.put("customer_email", subscription.getCustomerEmail());
        event.put("plan_id", subscription.getPlan().getId());
        event.put("next_billing_date", subscription.getNextBillingDate().toString());


        try {

            // serializa para json
            String message = objectMapper.writeValueAsString(event);

            // publica evento na exchange
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            System.out.println("Evento enviado -> " + message);

        }catch (JsonProcessingException e) {
            System.err.println("Erro ao serializar evento: " + e.getMessage());
        }

    }

}
