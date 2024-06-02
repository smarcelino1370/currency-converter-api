package br.com.currencyconverter.domain.transaction.model;

import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@Entity
@Table(name = "conversion_transaction")
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

    public static ConversionTransactionBuilder builder() {
        return new ConversionTransactionBuilder();
    }

    public ExchangeRate getExchangeRate() {
        return requireNonNull(exchangeRate);
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConversionTransaction that = (ConversionTransaction) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ConversionTransaction{" +
                "id=" + id +
                ", userId=" + userId +
                ", exchangeRate=" + exchangeRate +
                ", amount=" + amount +
                '}';
    }
}
