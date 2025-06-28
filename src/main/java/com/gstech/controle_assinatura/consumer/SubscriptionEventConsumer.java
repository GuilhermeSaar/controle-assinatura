package com.gstech.controle_assinatura.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.entities.Subscription;
import com.gstech.controle_assinatura.enums.EventType;
import com.gstech.controle_assinatura.enums.SubscriptionStatus;
import com.gstech.controle_assinatura.repository.EventRepository;
import com.gstech.controle_assinatura.repository.SubscriptionRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class SubscriptionEventConsumer {

    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleSubscriptionCreated(String message) {

        try {

            Map<String, Object> payload = objectMapper.readValue(message, Map.class);

            System.out.println("Evento recebido no Worker: " + payload);

            UUID subscriptionId = UUID.fromString((String) payload.get("id"));


            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));


            if(EventType.SUBSCRIPTION_CREATED.name().equals(payload.get("type"))) {
                subscription.setStatus(SubscriptionStatus.PENDENTE);
                System.out.println("Assinatura registrada no banco: " + subscriptionId);
            }
            else if (EventType.PAYMENT_SUCCESS.name().equalsIgnoreCase((String) payload.get("type"))) {

                subscription.setStatus(SubscriptionStatus.ATIVA);
                System.out.println("Assinatura ativada no banco: " + subscriptionId);
            }

            subscriptionRepository.save(subscription);

            eventRepository.findById(subscriptionId).ifPresent(event -> {
                event.setProcessed(true);
                eventRepository.save(event);
            });

        } catch (Exception e) {

            System.err.println("Erro ao processar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
