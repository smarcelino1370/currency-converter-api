package br.com.currencyconverter.infra.vo;

import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;

public final class ExchangeRateResponse {
    private final long timestamp;
    private final Map<String, BigDecimal> rates;

    @JsonCreator
    public ExchangeRateResponse(
            @JsonProperty("timestamp") long timestamp,
            @JsonProperty("rates") Map<String, BigDecimal> rates
    ) {
        this.timestamp = timestamp;
        this.rates = rates;
    }

    public BigDecimal getRate(CurrencyUnit origin, CurrencyUnit destination) {

        BigDecimal destinationRate = rates.get(destination.getCurrencyCode());

        if (isNull(destinationRate)) {
            throw new CurrencyWithoutExchangeRateException(destination.getCurrencyCode());
        }

        BigDecimal originRate = rates.get(origin.getCurrencyCode());

        if (isNull(originRate)) {
            throw new CurrencyWithoutExchangeRateException(origin.getCurrencyCode());
        }
        return destinationRate.divide(originRate, 6, RoundingMode.HALF_EVEN);
    }

    public LocalDateTime getLocalDateTime() {
        if (timestamp == 0L) {
            throw new InvalidDateException();
        }
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }

    @ResponseStatus(BAD_GATEWAY)
    public static class CurrencyWithoutExchangeRateException extends GatewayException {
        public CurrencyWithoutExchangeRateException(String currency) {
            super("Not found Exchange Rate from EUR to " + currency + "!");
        }
    }

    @ResponseStatus(BAD_GATEWAY)
    public static class InvalidDateException extends GatewayException {
        public InvalidDateException() {
            super("Transaction Date not found!");
        }
    }
}