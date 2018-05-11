package ru.glosav.gais.gateway.svc;

import dispatch.server.thrift.backend.*;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.gais.gateway.cfg.GaisClientConfig;
import ru.glosav.gais.gateway.dto.Application;
import ru.glosav.gais.gateway.dto.Company;
import ru.glosav.gais.gateway.util.DateUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class GaisConnectorService {
    Logger log = LoggerFactory.getLogger(GaisConnectorService.class);

    @Autowired
    private GaisClientConfig cfg;

    @Autowired
    private TSSLTransportFactory.TSSLTransportParameters tSSLTransportParameters;

    private TSocket socket;
    private TTransport transport;
    private TBinaryProtocol protocol;
    private DispatchBackend.Client client;
    private Session session;

    @PostConstruct
    public void init() {
        log.debug("GaisConnectorService.init");
    }

    public void send(Application application) throws TException {
        log.debug("GaisConnectorService.send: application id: {}", application.getId());
        ///////////// Находим группу m_groupname /////////////
        List<Group> groups = client.getRootGroups(session);
        groups.stream().forEach(group -> log.debug("Group: {}",group));

        Group primaryGroup = groups.get(0);
        Group russianCarrierGroup = primaryGroup.getTitle().equals(cfg.getGroup()) ?
                primaryGroup : null;
        log.debug("russianCarrierGroup found: id: {}, title: {}", russianCarrierGroup.getId(), russianCarrierGroup.getTitle());
    }

    @PreDestroy
    public void destroy() {

        log.debug("GaisConnectorService.destroy");
    }

    public void connect() throws TException {
        log.debug("GaisConnectorService.connect");
        if(cfg.isSslEnabled()) {
            log.debug("Use SSL socket");
            socket = TSSLTransportFactory.getClientSocket(
                    cfg.getHost(), cfg.getPort(),
                    30000, tSSLTransportParameters);
        } else {
            log.debug("Use no SSL socket");
            socket = new TSocket(cfg.getHost(), cfg.getPort());
            socket.setTimeout(5000);
            socket.open();
        }
        log.debug("GaisConnectorService.connect: socket on {}:{} opened", cfg.getHost(), cfg.getPort());
        transport = new TFramedTransport(socket);
        log.debug("GaisConnectorService.connect: Framed transport on {}:{} obtained", cfg.getHost(), cfg.getPort());
        protocol = new TBinaryProtocol(transport, true, true);
        log.debug("GaisConnectorService.connect: Binary protocol on {}:{} obtained", cfg.getHost(), cfg.getPort());
        client = new DispatchBackend.Client.Factory().getClient(protocol);
        log.debug("GaisConnectorService.connect: Dispatch backend client on {}:{} up", cfg.getHost(), cfg.getPort());
        session = client.login(cfg.getLogin(), cfg.getPassword(), false);
    }

    public void disconnect() {
        log.debug("GaisConnectorService.disconnect");

        try {
            client.logout(session);
            if (socket != null && socket.isOpen()) {
                socket.close();
            }
            log.debug("GiasConnectorService.disconnect: socket on {}:{} closed", cfg.getHost(), cfg.getPort());
        } catch (Exception e) {
            log.warn("Incorrect close TSocket on {}:{}, {}", cfg.getHost(), cfg.getPort(), e);
        }

    }

    public void list() throws Exception {
        log.debug("GaisConnectorService.list");
        synchronized (client) {
            List<Group> groups = client.getRootGroups(session);
            groups.forEach(group -> {
                log.debug("Group[ id: {}, title: '{}'", group.getId(), group.getTitle());
/*
                group.getAdditionalFields().getDataIterator().forEachRemaining(storeFieldValue -> {
                    log.debug(" >>> storeFieldValue: '{}'", storeFieldValue.getTitle());
                });
*/
                try {
                    List<Group> companyList = client.getChildrenGroups(session, group.getId(), false);
                    companyList.stream().forEach(company -> {
                        log.debug("Company: {}, {}, {}: {}",
                                company.getId(),
                                company.getTitle(),
                                company.getAdditionalFields().getData().get(3).getTitle(), // 3 - address
                                company.getAdditionalFields().getData().get(3).getValue());

/*
                        company.getAdditionalFields().getDataIterator().forEachRemaining(adf -> {
                            log.debug(" >>> storeFieldValue: '{}'=>'{}'", adf.getTitle(), adf.getValue());
                        });
*/
                        try {
                            List<MonitoringObject> monitoringObjects =
                                    client.getChildrenMonitoringObjects(session, company.getId(), false);
                            monitoringObjects.stream().forEach(mo->{
                                log.debug("--------> MO: {}", mo.getName());
                            });
                        } catch (TException e) {
                            e.printStackTrace();
                        }

                    });

                } catch (TException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void save(String groupName, Application a) throws Exception {
        log.debug("GaisConnectorService.save");
        synchronized (client) {
            List<Group> groups = client.getRootGroups(session);

            final Group[] russianCarrierGroup = new Group[1];

            groups.stream().forEach(group -> {
                if (group.getTitle().equals(groupName))
                    russianCarrierGroup[0] = group;
            });

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

/*
            extraFields.add(new StoreFieldValue()
                    .setTitle("extSessionId")
                    .setValue(a.getSessionId())); //
*/

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
                    = client.createCompanyWithAdditionalFields(session, russianCarrierGroup[0].getId(),
                    a.getCompany().getName(), // "ООО Адам Козлевич"
                    license,
                    new AdditionalFields(extraFields)
            );

            createTransportUnits(a.getCompany(), group);
        }
    }

    private void createTransportUnits(Company c, Group g) {
        log.debug("GaisConnectorService.createTransportUnits");
                Hibernate.initialize(c.getUnits());
                c.getUnits().stream().forEach(tu -> {
                    List<StoreFieldValue> extraFieldsOM = new ArrayList<StoreFieldValue>();
                    // обращаю внимание на то, что значения Title должны добуквенно
                    // соответствовать указанным, а в значения Value подставляться
                    // актуальные данные. ГРЗ и № трекера в extraFieldsOM НЕ указывается
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("Марка")
                            .setValue(tu.getType()));
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("Модель")
                            .setValue(tu.getModel()));
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("VIN")
                            .setValue(tu.getVin()));
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("Реестровый номер КТС")
                            .setValue(tu.getRnumber()));
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("Сведения о категорировании")
                            .setValue(tu.getCategory()));
                    extraFieldsOM.add(new StoreFieldValue()
                            .setTitle("ICCID SIM")
                            .setValue(tu.getIccid()));

                    Tracker tracker = new Tracker();

                    tracker.setVendor("EGTS"); // или "STUB" для псевдо-трекеров на этапе
                    // предварительной регистрации
                    tracker.setModel("EGTS"); // или "STUB" для псевдо-трекеров на этапе
                    // предварительной регистрации
                    tracker.addToIdentifier("123123123");

                    try {
                        MonitoringObject om
                                = client.createMonitoringObjectWithAdditionalFields(
                                session, g.getId(),
                                tracker,
                                tu.getGrn(), // ГРЗ
                                "#00ffff", // цвет отображения объекта в АСМ ЭРА, в формате RGB
                                "7", // номер иконки объекта в АСМ ЭРА, от "0" до "15"
                                new AdditionalFields(extraFieldsOM)
                        );
                        log.debug("Create transport unit '{}' for company '{}'", om.getId(), om.getName());
                    } catch (TException e) {
                        log.warn("Error create transport unit '{}' for company '{}'", tu.getGrn(), c.getName());
                    }

                });

    }


}
