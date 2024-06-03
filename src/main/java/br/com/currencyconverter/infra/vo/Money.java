package br.com.currencyconverter.infra.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

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
}
