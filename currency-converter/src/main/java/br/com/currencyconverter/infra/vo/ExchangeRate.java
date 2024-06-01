package br.com.currencyconverter.infra.vo;


import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import br.com.currencyconverter.infra.hibernate.type.ExchangeRateType;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CompositeTypeRegistration;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;

import static java.math.RoundingMode.HALF_EVEN;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;

@Getter
@RequiredArgsConstructor
@NoArgsConstructor(access = PRIVATE, force = true)
@Embeddable
@CompositeTypeRegistration(embeddableClass = ExchangeRate.class, userType = ExchangeRateType.class)
public class ExchangeRate implements Serializable {

    @Serial
    private static final long serialVersionUID = -9144887677357088290L;

    private final CurrencyUnit origin;
    private final CurrencyUnit destination;
    private final BigDecimal rate;
    private final LocalDateTime transactionDate;

    private ExchangeRate(ExchangeRateBuilder builder) {
        this.origin = requireNonNull(builder.origin);
        this.destination = requireNonNull(builder.destination);
        this.rate = requireNonNull(builder.getRate(this.origin, this.destination));
        this.transactionDate = requireNonNull(builder.getLocalDateTime());
    }

    public static ExchangeRateBuilder builder() {
        return new ExchangeRateBuilder();
    }

    public MonetaryAmount getOriginAmount(BigDecimal amount) {
        return Money.of(amount, this.origin);
    }

    public MonetaryAmount getDestinationAmount(BigDecimal amount) {
        return Money.of(amount.multiply(this.rate), this.destination);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ExchangeRate that = (ExchangeRate) object;
        return Objects.equals(origin, that.origin)
                && Objects.equals(destination, that.destination)
                && Objects.equals(rate, that.rate)
                && Objects.equals(transactionDate, that.transactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, destination, rate, transactionDate);
    }

    public static class ExchangeRateBuilder {

        private Map<String, BigDecimal> rates;
        private long timestamp;

        private CurrencyUnit origin;
        private CurrencyUnit destination;

        public ExchangeRateBuilder rates(Map<String, BigDecimal> rates) {
            this.rates = rates;
            return this;
        }

        public ExchangeRateBuilder timestamp(long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ExchangeRateBuilder origin(CurrencyUnit origin) {
            this.origin = origin;
            return this;
        }

        public ExchangeRateBuilder destination(CurrencyUnit destination) {
            this.destination = destination;
            return this;
        }

        public BigDecimal getRate(CurrencyUnit origin, CurrencyUnit destination) {

            if (isNull(this.rates)) {
                throw new CurrencyWithoutExchangeRateException(origin.getCurrencyCode());
            }

            BigDecimal destinationRate = rates.get(destination.getCurrencyCode());

            if (isNull(destinationRate)) {
                throw new CurrencyWithoutExchangeRateException(destination.getCurrencyCode());
            }

            BigDecimal originRate = rates.get(origin.getCurrencyCode());

            if (isNull(originRate)) {
                throw new CurrencyWithoutExchangeRateException(origin.getCurrencyCode());
            }
            return destinationRate.divide(originRate, 6, HALF_EVEN);
        }

        public LocalDateTime getLocalDateTime() {
            if (timestamp == 0L) {
                throw new InvalidDateException();
            }
            Instant instant = Instant.ofEpochSecond(timestamp);
            return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
        }

        public ExchangeRate build() {
            return new ExchangeRate(this);
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
}
