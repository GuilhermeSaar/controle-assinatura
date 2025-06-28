package com.gstech.controle_assinatura.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.entities.Event;
import com.gstech.controle_assinatura.entities.Subscription;
import com.gstech.controle_assinatura.enums.EventType;
import com.gstech.controle_assinatura.repository.EventRepository;
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
    @Autowired
    private EventRepository eventRepository;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    public void publisherSubscriptionCreatedEvent(Subscription subscription) {

        try {

            Map<String, Object> payload = new HashMap<>();
            payload.put("id", subscription.getId());
            payload.put("type", EventType.SUBSCRIPTION_CREATED.name());
            payload.put("customer_email", subscription.getCustomerEmail());
            payload.put("plan_id", subscription.getPlan().getId());
            payload.put("next_billing_date", subscription.getNextBillingDate().toString());

            String jsonPayload = objectMapper.writeValueAsString(payload);

            var event = new Event();
            event.setId(subscription.getId());
            event.setType(EventType.SUBSCRIPTION_CREATED);
            event.setData(jsonPayload);
            event.setProcessed(false);
            eventRepository.save(event);

            rabbitTemplate.convertAndSend(exchange, routingKey, jsonPayload);

            System.out.println("Evento salvo e enviado -> " + jsonPayload);


        } catch (Exception e) {
            System.err.println("Erro ao publicar evento: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
