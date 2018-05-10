package ru.glosav.gais.gateway.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
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
    @Column
    @ApiModelProperty(value = "Количество попыток отправки в ГАИС")
    private int attempts;

    public Session() {
        this.id = UUID.randomUUID().toString();
        this.created = Instant.now();
    }

}
