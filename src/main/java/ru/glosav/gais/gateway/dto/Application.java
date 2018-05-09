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
    @ApiModelProperty(value = "Номер заявки")
    private String number;
    @NotNull
    @ApiModelProperty(value = "Дата заявки, формат dd.MM.yyyy", example = "25.04.2018")
    @DateTimeFormat(pattern="dd.MM.yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern="dd.MM.yyyy", shape = JsonFormat.Shape.STRING)
    private Date appDate;
    @NotNull
    @ApiModelProperty(value = "Документ - основание заявки")
    private String base; // основание
    @NotNull
    @ApiModelProperty(value = "Номер документа - основания заявки")
    private String baseNumber; //
    @ApiModelProperty(value = "Заявляемая компания")
    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Company company;

    public static Group toTrift(
            DispatchBackend.Client client,
            dispatch.server.thrift.backend.Session s,
            String groupName,
            Application a) throws Exception {

        List<Group> groups = client.getRootGroups(s);

        final Group[] russianCarrierGroup = new Group[1];

        groups.stream().forEach(group -> {
            if (group.getTitle().equals(groupName))
                russianCarrierGroup[0] = group;
        });

/*


        if (russianCarrierGroup == null) {

            List<Group> childGroups = client.getChildrenGroups(
                    s,
                    primaryGroup.getId(),
                    true
            );

            for (Group g : childGroups) {
                if (g.title.equals(m_groupname)) {
                    russianCarrierGroup = g;
                    break;
                }
            }
        }
*/
        /////////////////////// Создаем компанию //////////////////////

        List<StoreFieldValue> extraFields = new ArrayList<StoreFieldValue>();
        // обращаю внимание на то, что значения Title должны добуквенно
        // соответствовать указанным, а в значения Value подставляться
        // актуальные данные. Наименование организации в extraFields
        // НЕ указывается
        extraFields.add(new StoreFieldValue()
                .setTitle("№ Договора")
                .setValue(a.getBaseNumber())); // "44567789900"
        extraFields.add(new StoreFieldValue()
                .setTitle("Дата Договора")
                .setValue(
                        DateUtil.parse(a.getAppDate()) // "01-01-2018"
                ));
        extraFields.add(new StoreFieldValue()
                .setTitle("ИНН")
                .setValue(a.getCompany().getInn())); // "155115802"
        extraFields.add(new StoreFieldValue()
                .setTitle("КПП")
                .setValue(a.getCompany().getKpp())); // "123423"
        extraFields.add(new StoreFieldValue()
                .setTitle("Адрес организации")
                .setValue(a.getCompany().getPaddress())); // "Ленинградское шоссе 80 к 16"
        extraFields.add(new StoreFieldValue()
                .setTitle("E-mail")
                .setValue(a.getCompany().getEmail())); // "eprosso@navitel.su"
        extraFields.add(new StoreFieldValue()
                .setTitle("Телефон")
                .setValue(a.getCompany().getPhone())); // "22233222"
        extraFields.add(new StoreFieldValue()
                .setTitle("Идентификатор ЕГИС ОТБ")
                .setValue(a.getCompany().getEgisOtbId())); // "ОТБ-1-2"

        License license = new License();
        // Время истечения лицензии в формате Unix timestamp,
        // 1546214400 соответствует 31.12.2018.
        // Можно не указывать для бесконечных лицензий
        if (a.getCompany().getExpireLicense() != null)
            license.setExpire(a.getCompany().getExpireLicense().getTime() / 1000);
        // Максимальное количество ОМ у перевозчика.
        // Можно не указывать, если не лимитировано особо
        // license.setMonitoringObjectsLimit(100);
        // Максимальное количество пользователей.
        // Для пользователей СИР число всегда равно 1
        license.setUsersLimit(1);
        // создаваемая компания находится во включенном состоянии
        license.setEnabled(true);

        // создается компания-перевозчик с подготовленными параметрами:
        Group group
                = client.createCompanyWithAdditionalFields(s, russianCarrierGroup[0].getId(),
                a.getCompany().getName(), // "ООО Адам Козлевич"
                license,
                new AdditionalFields(extraFields)
        );
        return group;
    }

}
