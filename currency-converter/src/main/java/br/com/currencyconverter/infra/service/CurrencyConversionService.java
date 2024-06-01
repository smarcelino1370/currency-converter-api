package br.com.currencyconverter.infra.service;

import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import br.com.currencyconverter.infra.vo.ExchangeRateResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RequiredArgsConstructor
@Service
public class CurrencyConversionService implements CurrencyConversionDomainService {

    private final ExternalApiProperties externalApiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public ExchangeRate handle(CurrencyUnit origin, CurrencyUnit destination) {
        return ExchangeRate.builder(get()).origin(origin).destination(destination).build();
    }

    private ExchangeRateResponse get() {

        try {

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(externalApiProperties.getFullUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.OK.value() != response.statusCode()) {
                throw new GatewayException(response.body());
            }

            return objectMapper.readValue(response.body(), ExchangeRateResponse.class);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new GatewayException(e.getMessage());
        } catch (Exception e) {
            throw new GatewayException(e.getMessage());
        }
    }
}
