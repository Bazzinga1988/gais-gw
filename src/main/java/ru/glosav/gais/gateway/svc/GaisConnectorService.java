package ru.glosav.gais.gateway.svc;

import dispatch.server.thrift.backend.DispatchBackend;
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
        log.debug("GiasConnectorService.init");
    }

    public void send(Application application) {
        log.debug("GiasConnectorService.send: {}, TSocket: {}", application, socket == null ? "null" : socket.toString());

    }

    @PreDestroy
    public void destroy() {

        log.debug("GiasConnectorService.destroy");
    }

    public void connect() throws TException {
        log.debug("GiasConnectorService.connect");
        if(cfg.isSslEnabled()) {
            log.debug("Use SSL socket");
            socket = TSSLTransportFactory.getClientSocket(
                    cfg.getHost(),
                    cfg.getPort(),
                    5000,
                    tSSLTransportParameters);
        } else {
            log.debug("Use no SSL socket");
            socket = new TSocket(cfg.getHost(), cfg.getPort());
            socket.setTimeout(360);
            socket.open();
        }
        log.debug("GiasConnectorService.connect: socket on {}:{} opened", cfg.getHost(), cfg.getPort());
        transport = new TFramedTransport(socket);
        log.debug("GiasConnectorService.connect: Framed transport on {}:{} obtained", cfg.getHost(), cfg.getPort());
        protocol = new TBinaryProtocol(transport, true, true);
        log.debug("GiasConnectorService.connect: Binary protocol on {}:{} obtained", cfg.getHost(), cfg.getPort());
        client = new DispatchBackend.Client.Factory().getClient(protocol);
        log.debug("GiasConnectorService.connect: Dispatch backend client on {}:{} up", cfg.getHost(), cfg.getPort());
        session = client.login(cfg.getLogin(), cfg.getPassword(), false);
    }

    public void disconnect() {
        log.debug("GiasConnectorService.disconnect");

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
