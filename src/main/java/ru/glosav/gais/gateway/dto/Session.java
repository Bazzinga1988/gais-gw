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
    @Column
    @NotNull
    @ApiModelProperty(value = "Дата и время создания сессии")
    private Instant created;
    @Column
    @NotNull
    @ApiModelProperty(value = "ID заявки")
    private long appId;
    @Column
    @NotNull
    @ApiModelProperty(value = "Признак успешной отправки в ГАИС")
    private boolean handled = false;
    @Column
    @ApiModelProperty(value = "Дата и время успешной отправки в ГАИС")
    private Instant handledDate;

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

    public boolean isHandled() {
        return handled;
    }

    public void setHandled(boolean handled) {
        this.handled = handled;
    }

    public Instant getHandledDate() {
        return handledDate;
    }

    public void setHandledDate(Instant handledDate) {
        this.handledDate = handledDate;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", created=" + created +
                ", appId=" + appId +
                ", handled=" + handled +
                '}';
    }
}
