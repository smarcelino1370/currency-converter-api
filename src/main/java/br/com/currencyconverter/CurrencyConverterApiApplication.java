package br.com.currencyconverter;

import br.com.currencyconverter.infra.provider.currencyconversion.ExternalApiProperties;
import br.com.currencyconverter.infra.provider.security.SecurityProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "br.com.currencyconverter")
@EnableConfigurationProperties({
        ExternalApiProperties.class,
        SecurityProperties.class
})
public class CurrencyConverterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApiApplication.class, args);
    }
}
