package ru.glosav.gais.gateway.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Entity
@ApiModel(value="TransportUnit", description="Модель данных описывающая транспортное средство")
public class TransportUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    @JsonIgnore
    private String sessionId;
    @NotNull
    @Column(unique = true)
    @ApiModelProperty(value = "Государственный регистрационный знак", example = "A-1234-дам")
    private String grn;
    @NotNull
    @ApiModelProperty(value = "Марка транспортного средства", example = "Lorraine Dietrich")
    private String type; // марка
    @NotNull
    @ApiModelProperty(value = "Модель транспортного средства", example = "Антилопа Гну")
    private String model;
    @NotNull
    @ApiModelProperty(value = "VIN транспортного средства", example = "00011122233")
    private String vin;
    @NotNull
    @ApiModelProperty(value = "Реестровый номер категорированного транспортного средства", example = "22233222")
    private String rnumber;
    @NotNull
    @ApiModelProperty(value = "Категория транспортного средства", example = "Первая")
    private String category;
    @NotNull
    @ApiModelProperty(value = "Идентификационный номер (IMEI) и модель АСН", example = "")
    private String imei;
    @NotNull
    @ApiModelProperty(value = "ICCID USIM-карты", example = "+79162223222")
    private String iccid;
    @NotNull
    @ApiModelProperty(value = "Абонентский номер USIM-карты", example = "")
    private String msisdn;

}
