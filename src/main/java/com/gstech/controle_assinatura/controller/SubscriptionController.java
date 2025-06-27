package com.gstech.controle_assinatura.controller;

import com.gstech.controle_assinatura.DTO.SubscriptionRequestDTO;
import com.gstech.controle_assinatura.DTO.SubscriptionResponseDTO;
import com.gstech.controle_assinatura.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@RequestBody SubscriptionRequestDTO data) {


        SubscriptionResponseDTO response = subscriptionService.createSubscription(data);

        return ResponseEntity.ok(response);
    }

}
