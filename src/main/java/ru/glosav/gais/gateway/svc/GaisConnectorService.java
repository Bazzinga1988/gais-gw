package ru.glosav.gais.gateway.svc;

import dispatch.server.thrift.backend.DispatchBackend;
import dispatch.server.thrift.backend.Group;
import dispatch.server.thrift.backend.Session;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.glosav.gais.gateway.cfg.GaisClientConfig;
import ru.glosav.gais.gateway.dto.Application;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
                    5000, tSSLTransportParameters);
        } else {
            log.debug("Use no SSL socket");
            socket = new TSocket(cfg.getHost(), cfg.getPort());
            socket.setTimeout(360);
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

}
