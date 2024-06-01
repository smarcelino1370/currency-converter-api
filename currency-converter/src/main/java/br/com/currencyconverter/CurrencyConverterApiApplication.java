package br.com.currencyconverter;

import br.com.currencyconverter.infra.service.ExternalApiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = " br.com.currencyconverter")
@EnableConfigurationProperties(ExternalApiProperties.class)
public class CurrencyConverterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApiApplication.class, args);
    }

}
