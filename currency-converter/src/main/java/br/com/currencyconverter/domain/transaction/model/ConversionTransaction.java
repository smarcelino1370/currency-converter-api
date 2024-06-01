package br.com.currencyconverter.domain.transaction.model;

import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(name = "conversion_transaction")
@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
public class ConversionTransaction {

    @EmbeddedId
    @AttributeOverride(name = TransactionConversionId.ATTRIBUTE, column = @Column(name = "id"))
    private final TransactionConversionId id;

    @AttributeOverride(name = UserId.ATTRIBUTE, column = @Column(name = "user_id"))
    private final UserId userId;

    private final ExchangeRate exchangeRate;

    private final BigDecimal amount;

    ConversionTransaction(ConversionTransactionBuilder builder) {
        this.id = requireNonNull(builder.id);
        this.userId = requireNonNull(builder.userId);
        this.exchangeRate = requireNonNull(builder.exchangeRate);
        this.amount = requireNonNull(builder.amount);
    }

    public ExchangeRate getExchangeRate() {
        return requireNonNull(exchangeRate);
    }

    public static ConversionTransactionBuilder builder() {
        return new ConversionTransactionBuilder();
    }

    public MonetaryAmount getOriginAmount() {
        return this.getExchangeRate().getOriginAmount(this.amount);
    }

    public MonetaryAmount getDestinationAmount() {
        return this.getExchangeRate().getDestinationAmount(requireNonNull(this.amount));
    }

    public BigDecimal getRate() {
        return this.getExchangeRate().getRate();
    }

    public LocalDateTime getTransactionDate() {
        return this.getExchangeRate().getTransactionDate();
    }
}
