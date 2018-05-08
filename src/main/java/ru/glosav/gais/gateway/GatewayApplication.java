package ru.glosav.gais.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
/*
@ComponentScan(
		basePackages = "ru.glosav.gais.gateway",
		excludeFilters=@ComponentScan.Filter(
				type = FilterType.REGEX,
				pattern="ru.glosav.gais.gateway.svc.GaisConnectorService*")
)
*/
public class GatewayApplication {

	public static void main(String[] args) {

		SpringApplication.run(GatewayApplication.class, args);

	}
}
