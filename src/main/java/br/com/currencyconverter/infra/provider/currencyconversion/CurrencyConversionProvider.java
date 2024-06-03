package br.com.currencyconverter.infra.provider.currencyconversion;

import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Service
public class CurrencyConversionProvider implements CurrencyConversionDomainService {

    private final HttpClient httpClient;
    private final ExternalApiProperties externalApiProperties;
    private final ObjectMapper objectMapper;

    @Override
    public ExchangeRate handle(CurrencyUnit origin, CurrencyUnit destination) {
        log.info("calling handle(CurrencyUnit, CurrencyUnit) - {} {}", origin, destination);
        ExchangeRateResponse exchangeRateResponse = get();
        return ExchangeRate.builder()
                .timestamp(exchangeRateResponse.timestamp())
                .rates(exchangeRateResponse.rates())
                .origin(origin)
                .destination(destination)
                .build();
    }

    private ExchangeRateResponse get() {

        log.info("calling get()");
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(externalApiProperties.getFullUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (HttpStatus.OK.value() != response.statusCode()) {
                log.error("error: status code is not OK: statusCode [{}]", response.statusCode());
                log.error("throwing {}", response.body());
                throw new GatewayException(response.body());
            }

            return objectMapper.readValue(response.body(), ExchangeRateResponse.class);

        } catch (InterruptedException e) {
            log.error("throwing ", e);
            Thread.currentThread().interrupt();
            throw new GatewayException(e.getMessage());
        } catch (Exception e) {
            log.error("throwing ", e);
            throw new GatewayException(e.getMessage());
        }
    }

    record ExchangeRateResponse(long timestamp, Map<String, BigDecimal> rates) {
    }
}
