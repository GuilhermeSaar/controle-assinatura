package com.gstech.controle_assinatura.entities;

import com.gstech.controle_assinatura.enums.EventType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tb_events")
public class Event {

    @Id
    private UUID id;
    @Column(name = "event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "json", nullable = false)
    private String data;

    @Column(nullable = false)
    private Boolean processed = false;

    public Event(UUID id, EventType type, String data, Boolean processed) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.processed = processed;
    }

    public Event() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getProcessed() {
        return processed;
    }

    public void setProcessed(Boolean processed) {
        this.processed = processed;
    }
}
