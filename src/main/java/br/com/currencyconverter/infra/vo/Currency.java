package br.com.currencyconverter.infra.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

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
}
