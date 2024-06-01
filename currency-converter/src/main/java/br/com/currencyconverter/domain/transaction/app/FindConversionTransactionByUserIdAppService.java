package br.com.currencyconverter.domain.transaction.app;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.domain.transaction.repository.ConversionTransactionRepository;
import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FindConversionTransactionByUserIdAppService implements FindConversionTransactionByUserIdUseCase {
    private final ConversionTransactionRepository conversionTransactionRepository;

    @Override
    public List<ConversionTransactionFinded> find(UserId userId) {
        List<ConversionTransaction> transactions = conversionTransactionRepository.findByUserId(userId);

        return transactions.stream()
                .map(ConversionTransactionFinded::of)
                .toList();
    }
}
