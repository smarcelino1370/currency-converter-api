package br.com.currencyconverter.domain.transaction.usecase;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.Currency;
import br.com.currencyconverter.infra.vo.Money;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface RegisterConversionTransactionUseCase {

    ConversionTransactionRegistered handle(RegisterConversionTransaction cmd, UserId userId);

    @Value
    class RegisterConversionTransaction {

        @NotNull(message = "Origin Currency must be informed!")
        Currency origin;

        @NotNull(message = "Destination Currency must be informed!")
        Currency destination;

        @NotNull(message = "Amount must be informed!")
        BigDecimal amount;
    }

    @Builder
    @Getter
    class ConversionTransactionRegistered {

        private final TransactionConversionId id;
        private final UserId userId;

        private final Money origin;
        private final Money destination;

        private final BigDecimal rate;
        private final LocalDateTime transactionDate;

        public static ConversionTransactionRegistered of(ConversionTransaction conversionTransaction) {
            return ConversionTransactionRegistered.builder()
                    .id(conversionTransaction.getId())
                    .userId(conversionTransaction.getUserId())
                    .origin(Money.from(conversionTransaction.getOriginAmount()))
                    .destination(Money.from(conversionTransaction.getDestinationAmount()))
                    .rate(conversionTransaction.getRate())
                    .transactionDate(conversionTransaction.getTransactionDate())
                    .build();
        }
    }

}
