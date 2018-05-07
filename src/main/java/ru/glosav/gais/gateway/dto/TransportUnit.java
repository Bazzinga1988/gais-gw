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
    @ApiModelProperty(value = "Государственный регистрационный знак")
    private String grn;
    @NotNull
    @ApiModelProperty(value = "Марка транспортного средства")
    private String type; // марка
    @NotNull
    @ApiModelProperty(value = "Модель транспортного средства")
    private String model;
    @NotNull
    @ApiModelProperty(value = "VIN транспортного средства")
    private String vin;
    @NotNull
    @ApiModelProperty(value = "Реестровый номер категорированного транспортного средства")
    private String rnumber;
    @NotNull
    @ApiModelProperty(value = "Идентификационный номер (IMEI) и модель АСН")
    private String imei;
    @NotNull
    @ApiModelProperty(value = "ICCID USIM-карты")
    private String iccid;
    @NotNull
    @ApiModelProperty(value = "Абонентский номер USIM-карты")
    private String msisdn;

}
