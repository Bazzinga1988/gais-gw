package ru.glosav.gais.gateway.dto;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class TransferLog {
    @Id
    private long tunit;
    @NotNull
    private String sessionId;
    @NotNull
    private String extId;

}
