package ru.glosav.gais.gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;


@Data
@Entity
@ApiModel(value="Application", description="Модель данных описывающая заявку")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull
    private String sessionId;
    @NotNull
    @ApiModelProperty(value = "Номер заявки")
    private String number;
    @NotNull
    @ApiModelProperty(value = "Дата заявки, формат dd.MM.yyyy", example = "25.04.2018")
    @DateTimeFormat(pattern="dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Instant appDate;
    @NotNull
    @ApiModelProperty(value = "Документ - основание заявки")
    private String base; // основание
    @ApiModelProperty(value = "Заявляемая компания")
    @NotNull
    @ManyToOne
    private Company company;
}