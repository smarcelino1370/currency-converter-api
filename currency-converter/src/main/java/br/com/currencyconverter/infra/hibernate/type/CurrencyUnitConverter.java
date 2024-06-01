package br.com.currencyconverter.infra.hibernate.type;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.money.CurrencyUnit;
import javax.money.Monetary;

import static java.util.Objects.nonNull;

@Converter(autoApply = true)
public class CurrencyUnitConverter implements AttributeConverter<CurrencyUnit, String> {
    @Override
    public String convertToDatabaseColumn(CurrencyUnit currencyUnit) {
        return nonNull(currencyUnit) ? currencyUnit.getCurrencyCode() : null;
    }

    @Override
    public CurrencyUnit convertToEntityAttribute(String currencyCode) {
        return nonNull(currencyCode) ? Monetary.getCurrency(currencyCode) : null;
    }
}
