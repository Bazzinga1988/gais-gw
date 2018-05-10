package ru.glosav.gais.gateway.cfg;

import lombok.Data;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.glosav.gais.gateway.ctrl.ApplicationController;

@Configuration
@Data
public class GaisClientConfig {
    Logger log = LoggerFactory.getLogger(GaisClientConfig.class);

    @Value("${gaisclient.ssl.enabled}")
    private boolean sslEnabled;
    @Value("${gaisclient.ssl.truststore}")
    private String keyStore;
    @Value("${gaisclient.ssl.passphrase}")
    private String passphrase;
    @Value("${gaisclient.host}")
    private String host;
    @Value("${gaisclient.group}")
    private String group;
    @Value("${gaisclient.port}")
    private int port;
    @Value("${gaisclient.login}")
    private String login;
    @Value("${gaisclient.password}")
    private String password;

    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }


    @Bean
    TSSLTransportFactory.TSSLTransportParameters tSSLTransportParameters() {
        TSSLTransportFactory.TSSLTransportParameters param =
                new TSSLTransportFactory.TSSLTransportParameters();
        log.debug("Keystore: {}, passphrase: ", keyStore, passphrase);
        param.setTrustStore(keyStore, passphrase);
        return param;
    }
}
