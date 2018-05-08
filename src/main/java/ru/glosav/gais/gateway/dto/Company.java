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
import java.util.List;
import java.util.Set;

@Data
@Entity
@ApiModel(value="Company", description="Модель данных описывающая компанию")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    @NotNull
    @ApiModelProperty(value = "Название компании")
    private String name;
    @NotNull
    @Column(unique = true)
    @ApiModelProperty(value = "ИНН компании")
    private String inn;
    @NotNull
    @ApiModelProperty(value = "КПП компании")
    private String kpp;
    @NotNull
    @ApiModelProperty(value = "ОГРН компании")
    private String ogrn;
    @NotNull
    @ApiModelProperty(value = "Физический адрес компании")
    private String paddress;
    @NotNull
    @ApiModelProperty(value = "Юридический адрес компании")
    private String laddress;
    @NotNull
    @ApiModelProperty(value = "Расчетный счет компании")
    private String checkingAccount;
    @NotNull
    @ApiModelProperty(value = "Банк компании")
    private String bank;
    @NotNull
    @ApiModelProperty(value = "БИК компании")
    private String bik;
    @NotNull
    @ApiModelProperty(value = "Корреспонденский счет компании")
    private String corrAccount;
    @NotNull
    @ApiModelProperty(value = "Адрес электронной почты")
    private String email;
    @NotNull
    @ApiModelProperty(value = "Телефон компании")
    private String phone;
    @NotNull
    @ApiModelProperty(value = "Идентификатор ЕГИС ОТБ")
    private String egisOtbId;
    @NotNull
    @ApiModelProperty(value = "Время окончания лицензии, формат dd.MM.yyyy", example = "25.04.2019")
    @DateTimeFormat(pattern="dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date expireLicense;
    @NotNull
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    // @JoinColumn(name = "company_id")
    @ApiModelProperty(value = "Список регистрируемых транспортных средств компании")
    private List<TransportUnit> units;

}
