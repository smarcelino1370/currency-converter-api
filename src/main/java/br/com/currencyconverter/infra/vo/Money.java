package br.com.currencyconverter.infra.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

@Getter
public class Money {

    private final Currency currency;
    private final BigDecimal amount;

    public Money(Currency currency, BigDecimal amount) {
        this.currency = requireNonNull(currency);
        this.amount = requireNonNull(amount);
    }

    @JsonCreator
    public static Money from(MonetaryAmount monetaryAmount) {
        return new Money(
                Currency.from(monetaryAmount.getCurrency().getCurrencyCode()),
                BigDecimal.valueOf(monetaryAmount.getNumber().doubleValue())
        );
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Money money = (Money) object;
        return Objects.equals(currency, money.currency) && Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Money.class.getSimpleName() + "[", "]")
                .add("currency=" + currency)
                .add("amount=" + amount)
                .toString();
    }
}
