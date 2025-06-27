package com.gstech.controle_assinatura.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.entities.Subscription;
import com.gstech.controle_assinatura.enums.SubscriptionStatus;
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
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleSubscriptionCreated(String message) {

        try {

            Map<String, Object> payload = objectMapper.readValue(message, Map.class);

            System.out.println("Evento recebido no Worker: " + payload);

            UUID subscriptionId = UUID.fromString((String) payload.get("id"));

            // busca a assinatura no banco
            Subscription subscription = subscriptionRepository.findById(subscriptionId)
                    .orElseThrow(() -> new RuntimeException("Assinatura não encontrada"));

            // Atualiza status para ativa ou o que o evento indicar
            subscription.setStatus(SubscriptionStatus.ATIVA);


            subscriptionRepository.save(subscription);

            System.out.println("Assinatura ativada no banco: " + subscriptionId);


        } catch (Exception e) {

            System.err.println("Erro ao processar evento: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
