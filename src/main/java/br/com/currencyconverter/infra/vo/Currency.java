package br.com.currencyconverter.infra.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import java.util.Objects;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

public class Currency {

    @JsonValue
    private final String code;

    private Currency(String code) {
        this.code = requireNonNull(code);
    }

    @JsonCreator
    public static Currency from(String code) {
        return new Currency(code);
    }

    @JsonIgnore
    public CurrencyUnit getCurrencyUnit() {
        return Monetary.getCurrency(this.code);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Currency currency = (Currency) object;
        return Objects.equals(code, currency.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Currency.class.getSimpleName() + "[", "]")
                .add("code='" + code + "'")
                .toString();
    }
}
