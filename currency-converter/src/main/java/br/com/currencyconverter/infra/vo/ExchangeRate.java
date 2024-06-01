package br.com.currencyconverter.infra.vo;


import br.com.currencyconverter.infra.hibernate.type.ExchangeRateType;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CompositeTypeRegistration;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

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
        this.rate = requireNonNull(builder.exchangeRates.getRate(this.origin, this.destination));
        this.transactionDate = requireNonNull(builder.exchangeRates.getLocalDateTime());
    }

    public static ExchangeRateBuilder builder(ExchangeRateResponse exchangeRates) {
        return new ExchangeRateBuilder(requireNonNull(exchangeRates));
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

        private final ExchangeRateResponse exchangeRates;
        private CurrencyUnit origin;
        private CurrencyUnit destination;

        ExchangeRateBuilder(ExchangeRateResponse exchangeRates) {
            this.exchangeRates = exchangeRates;
        }

        public ExchangeRateBuilder origin(CurrencyUnit origin) {
            this.origin = origin;
            return this;
        }

        public ExchangeRateBuilder destination(CurrencyUnit destination) {
            this.destination = destination;
            return this;
        }

        public ExchangeRate build() {
            return new ExchangeRate(this);
        }
    }
}
