package ru.glosav.gais.gateway;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class GatewayApplication {

	@Resource
	private MetricRegistry metricRegistry;

	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);

	}

	@PostConstruct
	private void init() {
		final JmxReporter reporter = JmxReporter.forRegistry(metricRegistry)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		reporter.start();

		Slf4jReporter slf4jReporter = Slf4jReporter.forRegistry(metricRegistry)
				.outputTo(LoggerFactory.getLogger("ru.glosav.gais.gateway"))
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS)
				.build();
		slf4jReporter.start(60, TimeUnit.SECONDS);

        /*
        final Cassandra cassandra = new Cassandra(
                cfg.getCassandraServers(),
                "metrics",
                "data",
                31536000,
                9042,
                "ONE");

        final CassandraReporter cassandraReporter = CassandraReporter.forRegistry(metricRegistry)
                .prefixedWith("k3.ep.1.metrics")
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.MILLISECONDS)
                .filter(MetricFilter.ALL)
                .build(cassandra);
        cassandraReporter.start(1, TimeUnit.MINUTES);
        */
	}

}
