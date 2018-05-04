package ru.glosav.gais.gateway.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;


@Data
@Entity
@ApiModel(value="Application", description="Модель данных описывающая заявку")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String sessionId;
    @NotNull
    private String number;
    @NotNull
    private Instant date;
    private Instant sended;
    @NotNull
    private String base; // основание
    @NotNull
    @ManyToOne
    private Company company;
}
