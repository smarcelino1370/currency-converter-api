package br.com.currencyconverter.domain.transaction.model;

import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static lombok.AccessLevel.PACKAGE;

@NoArgsConstructor(access = PACKAGE)
public class ConversionTransactionBuilder {

    protected TransactionConversionId id;

    protected UserId userId;

    protected ExchangeRate exchangeRate;

    protected BigDecimal amount;

    public ConversionTransactionBuilder userId(UserId userId) {
        this.userId = userId;
        return this;
    }

    public ConversionTransactionBuilder exchangeRate(ExchangeRate exchangeRate) {
        this.exchangeRate = exchangeRate;
        return this;
    }

    public ConversionTransactionBuilder amount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public ConversionTransaction build() {
        this.id = TransactionConversionId.generate();
        return new ConversionTransaction(this);
    }
}
