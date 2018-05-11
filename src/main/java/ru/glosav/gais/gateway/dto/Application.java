package ru.glosav.gais.gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import dispatch.server.thrift.backend.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.glosav.gais.gateway.util.DateUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Data
@Entity
@ApiModel(value="Application", description="Модель данных описывающая заявку")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull
    @JsonIgnore
    private String sessionId;
    @NotNull
    @Column(unique = true)
    @ApiModelProperty(value = "Номер заявки", example = "25042018")
    private String number;
    @NotNull
    @ApiModelProperty(value = "Дата заявки, формат dd.MM.yyyy", example = "25.04.2018")
    @DateTimeFormat(pattern="dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date appDate;
    @NotNull
    @ApiModelProperty(value = "Документ - основание заявки", example = "Договор")
    private String base; // основание
    @NotNull
    @ApiModelProperty(value = "Номер документа - основания заявки", example = "2504")
    private String baseNumber; //
    @ApiModelProperty(value = "Заявляемая компания")
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Company company;


}
