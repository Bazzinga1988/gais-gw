package ru.glosav.gais.gateway;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Value("${gaisclient.ssl.truststore}")
    private String keyStore;
    @Value("${gaisclient.ssl.passphrase}")
    private String passphrase;
    @Value("${gaisclient.host}")
    private String host;
    @Value("${gaisclient.port}")
    private int port;

    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    TSocket tSocket(TSSLTransportFactory.TSSLTransportParameters tSSLTransportParameters) throws TTransportException {
        return TSSLTransportFactory.getClientSocket(host, port, 5000, tSSLTransportParameters);
    }

    @Bean
    TSSLTransportFactory.TSSLTransportParameters tSSLTransportParameters() {
        TSSLTransportFactory.TSSLTransportParameters param =
                new TSSLTransportFactory.TSSLTransportParameters();
        param.setKeyStore(keyStore, passphrase);
        return param;
    }
}
