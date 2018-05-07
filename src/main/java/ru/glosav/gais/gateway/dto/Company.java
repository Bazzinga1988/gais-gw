package ru.glosav.gais.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @OneToMany(mappedBy = "company", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
    // @JoinColumn(name = "company_id")
    @ApiModelProperty(value = "Список регистрируемых транспортных средств компании")
    private List<TransportUnit> units;

}
