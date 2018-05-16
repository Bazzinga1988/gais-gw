package ru.glosav.gais.gateway.svc;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
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
import ru.glosav.gais.gateway.dto.TransferLog;
import ru.glosav.gais.gateway.repo.TransferLogRepository;
import ru.glosav.gais.gateway.util.DateUtil;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class GaisConnectorService {
    Logger log = LoggerFactory.getLogger(GaisConnectorService.class);

    @Autowired
    private GaisClientConfig cfg;

    @Autowired
    private TSSLTransportFactory.TSSLTransportParameters tSSLTransportParameters;

    @Autowired
    TransferLogRepository transferLogRepository;

    @Resource
    private MetricRegistry metricRegistry;

    private TSocket socket;
    private TTransport transport;
    private TBinaryProtocol protocol;
    private DispatchBackend.Client client;
    private Session session;


    private Counter companySuccessCounter = null;
    private Counter companyErrorCounter = null;
    private Counter unitSuccessCounter = null;
    private Counter unitErrorCounter = null;

    private Timer sendTimer = null;
    private Timer listCompaniesTimer = null;


    @PostConstruct
    public void init() {
        log.debug("GaisConnectorService.init");
        companySuccessCounter = metricRegistry.counter("SEND_COMPANY_SUCCESS_COUNTER");
        companyErrorCounter = metricRegistry.counter("SEND_COMPANY_ERROR_COUNTER");
        unitSuccessCounter = metricRegistry.counter("SEND_UNIT_SUCCESS_COUNTER");
        unitErrorCounter = metricRegistry.counter("SEND_UNIT_ERROR_COUNTER");
        sendTimer = metricRegistry.timer("GaisConnectorService::send");
        listCompaniesTimer = metricRegistry.timer("GaisConnectorService::listCompanies");
    }


    @PreDestroy
    public void destroy() {

        log.debug("GaisConnectorService.destroy");
    }

    public void connect() throws TException {
        log.debug("GaisConnectorService.connect");
        if (cfg.isSslEnabled()) {
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
        session = client.login(cfg.getLogin(), cfg.getPassword(), true);
    }

    public void disconnect() {
        log.debug("GaisConnectorService.disconnect");
        synchronized (client) {
            try {
                client.logout(session);
                if (socket != null && socket.isOpen()) {
                    socket.close();
                }
                log.debug("GiasConnectorService.disconnect: socket on {}:{} closed", cfg.getHost(), cfg.getPort());
            } catch (Exception e) {
                log.warn("Incorrect close TSocket on {}:{}", cfg.getHost(), cfg.getPort(), e);
            }
        }
    }

    public void list() throws Exception {
        log.debug("GaisConnectorService.list");
        final Timer.Context context = listCompaniesTimer.time();
        synchronized (client) {
            List<Group> groups = client.getRootGroups(session);
            for(Group group: groups){
                log.debug("Group[ id: {}, title: '{}']", group.getId(), group.getTitle());
                try {
                    List<Group> companyList = client.getChildrenGroups(session, group.getId(), false);
                    for(Group company: companyList) {
                        log.debug("Company: [{}, {}]", company.getId(), company.getTitle());
                    }
                } catch (TException e) {
                    log.error("Error obtain company", e);
                }
            }
        }
        context.stop();
    }

    public void save(String groupName, Application a) throws Exception {
        log.debug("GaisConnectorService.send");
        final Timer.Context context = sendTimer.time();
        TransferLog transferLog = new TransferLog();
        transferLog.setType(TransferLog.Type.COMPANY);
        transferLog.setObjId(a.getCompany().getId());
        transferLog.setSessionId(a.getSessionId());

        synchronized (client) {
            try {

                List<Group> groups = client.getRootGroups(session);

                final Group[] russianCarrierGroup = new Group[1];

                groups.stream().forEach(group -> {
                    if (group.getTitle().equals(groupName))
                        russianCarrierGroup[0] = group;
                });

                List<StoreFieldValue> extraFields = fillStoreFieldValue(a);

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

                try {
                    // создается компания-перевозчик с подготовленными параметрами:
                    Group group
                            = client.createCompanyWithAdditionalFields(session, russianCarrierGroup[0].getId(),
                            a.getCompany().getName(), // "ООО Адам Козлевич"
                            license,
                            new AdditionalFields(extraFields)
                    );
                    transferLog.setExtId(group.getId());
                    transferLog.setResult(TransferLog.Result.SUCCESS);
                    transferLog.setMsg(TransferLog.Result.SUCCESS.name());
                    companySuccessCounter.inc();
                    saveLog(transferLog);
                    createTransportUnits(a.getSessionId(), a.getCompany(), group);
                } catch (Exception e) {
                    companyErrorCounter.inc();
                    fillErrorLogCompany(a, transferLog, e);
                    saveLog(transferLog);
                    log.warn("Error send company id: {}, name: '{}' {}", a.getCompany().getId(), a.getCompany().getName(), e);
                }
            } catch (Exception e) {
                saveLog(fillErrorLogCompany(a, transferLog, e));
                log.warn("Error prepeare to transfer company id: {}, name: '{}' {}", a.getCompany().getId(), a.getCompany().getName(), e);
            } finally {
                context.stop();
            }
        }

    }

    private TransferLog fillErrorLogCompany(Application a, TransferLog transferLog, Exception e) {
        transferLog.setExtId(null);
        transferLog.setResult(TransferLog.Result.ERROR);
        transferLog.setMsg(TransferLog.Result.ERROR.name() + ": " + e.getMessage());
        return transferLog;
    }

    private void createTransportUnits(String sessionId, Company c, Group g) {
        log.debug("GaisConnectorService.createTransportUnits");
        Hibernate.initialize(c.getUnits());
        c.getUnits().stream().forEach(tu -> {
            log.debug("Try save TU: {}", tu);
            tu.setSessionId(sessionId);
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
            tracker.addToIdentifier(tu.getImei());
            tracker.setPhoneNumber(tu.getMsisdn());


            TransferLog transferLog = new TransferLog();
            transferLog.setType(TransferLog.Type.TRANSPORT_UNIT);
            transferLog.setObjId(tu.getId());
            transferLog.setSessionId(sessionId);
            try {
                MonitoringObject om
                        = client.createMonitoringObjectWithAdditionalFields(
                        session,
                        g.getId(),
                        tracker,
                        tu.getGrn(), // ГРЗ
                        "#00ffff", // цвет отображения объекта в АСМ ЭРА, в формате RGB
                        "7", // номер иконки объекта в АСМ ЭРА, от "0" до "15"
                        new AdditionalFields(extraFieldsOM)
                );
                transferLog.setExtId(om.getId());
                transferLog.setResult(TransferLog.Result.SUCCESS);
                transferLog.setMsg(TransferLog.Result.SUCCESS.name());
                unitSuccessCounter.inc();
                saveLog(transferLog);
                log.info("Create transport unit '{}' for company '{}'", om.getId(), om.getName());
            } catch (TException e) {
                transferLog.setExtId(null);
                transferLog.setResult(TransferLog.Result.ERROR);
                transferLog.setMsg(TransferLog.Result.ERROR.name() + ": " + e.getMessage());
                saveLog(transferLog);
                unitErrorCounter.inc();
                log.warn("Error send transport unit '{}' for company '{}'", tu.getGrn(), c.getName(), e);
            }
        });
    }

    public List<StoreFieldValue> fillStoreFieldValue(Application a) {
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
        return extraFields;
    }

    public void saveLog(TransferLog transferLog) {
        try {
            transferLogRepository.save(transferLog);
        } catch (Exception e) {
            log.error("Error save transfer log: {}", transferLog.toString(), e);
        }
    }
}
