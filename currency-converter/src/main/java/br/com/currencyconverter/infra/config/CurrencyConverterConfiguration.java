package br.com.currencyconverter.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.http.HttpClient;

@Configuration
public class CurrencyConverterConfiguration {

    @Primary
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newHttpClient();
    }
}
