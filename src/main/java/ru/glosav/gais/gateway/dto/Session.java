package ru.glosav.gais.gateway.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Entity
public class Session {
    @Id
    @ApiModelProperty(value = "UUID сессии")
    private String id;
    @NotNull
    @ApiModelProperty(value = "Дата и время создания сессии")
    public Instant created;
    @NotNull
    @ApiModelProperty(value = "ID заявки")
    public long appId;

    public Session() {
        this.id = UUID.randomUUID().toString();
        this.created = Instant.now();
    }

    public String getId() {
        return id;
    }

    public Instant getCreated() {
        return created;
    }


    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

}
