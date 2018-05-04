package ru.glosav.gais.gateway.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "Номер заявки")
    private String number;
    @NotNull
    @ApiModelProperty(value = "Дата заявки")
    private Instant date;
    private Instant sended;
    @NotNull
    @ApiModelProperty(value = "Документ - основание заявки")
    private String base; // основание
    @ApiModelProperty(value = "Заявляемая компания")
    @NotNull
    @ManyToOne
    private Company company;
}
