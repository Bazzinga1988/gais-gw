package ru.glosav.gais.gateway.dto;


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
    private long id;
    @ManyToOne
    @JoinColumn(name = "company_fk", insertable=false, updatable=false)
    private Company application;
    @NotNull
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
    private String rnumber;
    @NotNull
    private String imei;
    @NotNull
    private String iccid;
    @NotNull
    private String msisdn;

}
