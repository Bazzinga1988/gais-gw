package ru.glosav.gais.gateway.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@ToString
@Entity
@ApiModel(value="TransportUnit", description="Модель данных описывающая транспортное средство")
public class TransportUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;

    @JsonIgnore
    @Column
    private long companyId;
    @JsonIgnore
    private String sessionId;
    @NotNull
    private String sourceId;
    @NotNull
    @Column
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
    @ApiModelProperty(value = "Идентификационный номер (IMEI) и модель АСН", example = "35-419002-389644-3")
    private String imei;
    @NotNull
    @ApiModelProperty(value = "ICCID USIM-карты", example = "+MMCC IINN NNNN NNNN NN C x")
    private String iccid;
    @NotNull
    @ApiModelProperty(value = "Абонентский номер USIM-карты", example = "+79162223222")
    private String msisdn;

}
