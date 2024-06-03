package br.com.currencyconverter.domain.transaction.usecase;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.infra.identifiers.TransactionConversionId;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.Builder;
import lombok.Getter;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface FindConversionTransactionByUserIdUseCase {
    List<ConversionTransactionFinded> find(UserId userId);

    @Builder
    @Getter
    class ConversionTransactionFinded {

        private final TransactionConversionId id;
        private final UserId userId;

        private final MonetaryAmount origin;
        private final MonetaryAmount destination;

        private final BigDecimal rate;
        private final LocalDateTime transactionDate;

        public static ConversionTransactionFinded of(ConversionTransaction conversionTransaction) {
            return ConversionTransactionFinded.builder()
                    .id(conversionTransaction.getId())
                    .userId(conversionTransaction.getUserId())
                    .origin(conversionTransaction.getOriginAmount())
                    .destination(conversionTransaction.getDestinationAmount())
                    .rate(conversionTransaction.getRate())
                    .transactionDate(conversionTransaction.getTransactionDate())
                    .build();
        }
    }
}
