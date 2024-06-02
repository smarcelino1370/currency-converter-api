package br.com.currencyconverter;

import br.com.currencyconverter.infra.provider.currencyconversion.ExternalApiProperties;
import br.com.currencyconverter.infra.provider.security.SecurityProperties;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "br.com.currencyconverter")
@OpenAPIDefinition(info = @Info(title = "Currency Conversion API", version = "v1", description = "A REST API to Currency Conversion"))
@EnableConfigurationProperties({
        ExternalApiProperties.class,
        SecurityProperties.class
})
public class CurrencyConverterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApiApplication.class, args);
    }
}
