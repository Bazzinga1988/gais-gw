package ru.glosav.gais.gateway.dto;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private String inn;
    @NotNull
    private String kpp;
    @NotNull
    private String ogrn;
    @NotNull
    private String paddress;
    @NotNull
    private String laddress;
    @NotNull
    private String checkingAccount;
    @NotNull
    private String bank;
    @NotNull
    private String bik;
    @NotNull
    private String corrAccount;
    @NotNull
    @OneToMany
    @JoinColumn(name = "company_fk")
    private Set<TransportUnit> units;

}
