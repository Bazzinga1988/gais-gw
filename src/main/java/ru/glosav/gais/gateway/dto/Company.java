package ru.glosav.gais.gateway.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
/*
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"inn", "kpp"})
})
*/
@ApiModel(value="Company", description="Модель данных описывающая компанию")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull
    @ApiModelProperty(value = "Название компании", example = "ООО Адам Козлевич")
    private String name;
    @NotNull
    @Column(unique = true)
    @ApiModelProperty(value = "ИНН компании", example = "155115802")
    private String inn;
    @ApiModelProperty(value = "КПП компании", example = "123423")
    private String kpp;
    @NotNull
    @ApiModelProperty(value = "ОГРН компании", example = "")
    private String ogrn;
    @NotNull
    @ApiModelProperty(value = "Физический адрес компании", example = "Ленинградское шоссе 80 к 16")
    private String paddress;
    @ApiModelProperty(value = "Юридический адрес компании", example = "Ленинградское шоссе 80 к 12")
    private String laddress;
    @NotNull
    @ApiModelProperty(value = "Расчетный счет компании", example = "12345235636536")
    private String checkingAccount;
    @NotNull
    @ApiModelProperty(value = "Банк компании", example = "ТрастБанк")
    private String bank;
    @NotNull
    @ApiModelProperty(value = "БИК компании", example = "3452435345")
    private String bik;
    @NotNull
    @ApiModelProperty(value = "Корреспонденский счет компании", example = "234523452345345")
    private String corrAccount;
    @NotNull
    @ApiModelProperty(value = "Адрес электронной почты", example = "eprosso@navitel.su")
    private String email;
    @NotNull
    @ApiModelProperty(value = "Телефон компании", example = "89998887755")
    private String phone;
    @NotNull
    @ApiModelProperty(value = "Идентификатор ЕГИС ОТБ", example = "ОТБ-1-2")
    private String egisOtbId;
    @ApiModelProperty(value = "Время окончания лицензии, формат dd.MM.yyyy", example = "30.07.2018")
    @DateTimeFormat(pattern="dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date expireLicense;
    @NotNull
    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @ApiModelProperty(value = "Список регистрируемых транспортных средств компании")
    private List<TransportUnit> units;

}
