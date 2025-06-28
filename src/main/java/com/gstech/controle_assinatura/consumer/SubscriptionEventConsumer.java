package com.gstech.controle_assinatura.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.entities.Event;
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

            var subscriptionId = UUID.fromString((String) payload.get("id"));
            var eventTypeStr = (String) payload.get("type");
            var eventType = EventType.valueOf(eventTypeStr.toUpperCase());


            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));


            if (eventType == EventType.SUBSCRIPTION_CREATED) {
                subscription.setStatus(SubscriptionStatus.PENDENTE);
                System.out.println("Assinatura aguardando o pagamento: " + subscriptionId);
            }

            else if (eventType == EventType.PAYMENT_SUCCESS) {
                subscription.setStatus(SubscriptionStatus.ATIVA);
                System.out.println("Pagamento confirmado. Assinatura ativada: " + subscriptionId);
            }

            subscriptionRepository.save(subscription);

            var event = new Event();
            event.setId(UUID.randomUUID());
            event.setType(eventType);
            event.setProcessed(true);
            event.setData(message);
            eventRepository.save(event);

            System.out.println("Evento registrado no banco: " + event.getId());

        } catch (Exception e) {

            System.err.println("Erro ao processar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
