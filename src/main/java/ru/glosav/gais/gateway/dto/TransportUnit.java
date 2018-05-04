package ru.glosav.gais.gateway.dto;


import io.swagger.annotations.ApiModel;
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
    private String grn;
    @NotNull
    private String type; // марка
    @NotNull
    private String model;
    @NotNull
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
