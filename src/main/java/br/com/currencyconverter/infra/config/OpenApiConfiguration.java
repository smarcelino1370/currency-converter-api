package br.com.currencyconverter.infra.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeIn.HEADER;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Currency Conversion API", version = "v1", description = "A REST API to Currency Conversion")
)
@SecurityScheme(
        name = "bearerAuth",
        type = HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = HEADER
)
public class OpenApiConfiguration {
}
