package com.gstech.controle_assinatura.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gstech.controle_assinatura.DTO.EventPayloadDTO;
import com.gstech.controle_assinatura.DTO.PaymentWebhookDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webhooks")
public class WebhookController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.routingKey}")
    private String routingKey;

    @PostMapping(value = "/payment")
    public ResponseEntity<Void> handlePaymentWebhook(@RequestBody PaymentWebhookDTO data) {

        try {

            String message = objectMapper.writeValueAsString(paymentWebhookDTOForEventPayloadDTO(data));
            rabbitTemplate.convertAndSend(exchange, routingKey, message);

            System.out.println("📨 Webhook de pagamento enviado para fila: " + message);
            return ResponseEntity.ok().build();

        }catch (JsonProcessingException e) {
            System.err.println("Erro ao serializar PaymentWebhook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        catch (Exception e) {

            System.err.println("Erro ao processar webhook: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    private EventPayloadDTO paymentWebhookDTOForEventPayloadDTO(PaymentWebhookDTO data) {

        return new EventPayloadDTO(
                data.subscriptionId(),
                data.event(),
                data.amount(),
                data.date()
        );
    }
}
