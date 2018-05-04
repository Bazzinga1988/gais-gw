package ru.glosav.gais.gateway.dto;

import java.util.UUID;

public class Session {
    private String id;

    public Session() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                '}';
    }
}
