package br.com.currencyconverter.infra.service;

import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CurrencyConversionService implements CurrencyConversionDomainService {

    private final HttpClient httpClient;
    private final ExternalApiProperties externalApiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public ExchangeRate handle(CurrencyUnit origin, CurrencyUnit destination) {
        ExchangeRateResponse exchangeRateResponse = get();
        return ExchangeRate.builder()
                .timestamp(exchangeRateResponse.timestamp())
                .rates(exchangeRateResponse.rates())
                .origin(origin)
                .destination(destination)
                .build();
    }

    private ExchangeRateResponse get() {

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(externalApiProperties.getFullUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

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

    record ExchangeRateResponse(long timestamp, Map<String, BigDecimal> rates) {
    }
}
