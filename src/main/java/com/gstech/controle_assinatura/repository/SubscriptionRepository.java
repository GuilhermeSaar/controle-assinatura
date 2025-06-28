package com.gstech.controle_assinatura.repository;

import com.gstech.controle_assinatura.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    @Query(value = "SELECT status, " +
            "COUNT(*) AS total " +
            "FROM tb_subscriptions " +
            "GROUP BY status", nativeQuery = true)
    List<Object[]> countByStatus();


    @Query(value = "SELECT p.id AS plan_id, p.name AS plan_name, " +
            "COUNT(s.id) AS active_subscriptions " +
            "FROM tb_plans p " +
            "LEFT JOIN tb_subscriptions s ON s.plan_id = p.id AND s.status = 'ATIVA' " +
            "GROUP BY p.id, p.name", nativeQuery = true)
    List<Object[]> countActiveSubscriptionsPerPlan();
}

