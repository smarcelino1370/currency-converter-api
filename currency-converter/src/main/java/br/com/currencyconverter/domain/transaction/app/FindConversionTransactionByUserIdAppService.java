package br.com.currencyconverter.domain.transaction.app;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.domain.transaction.repository.ConversionTransactionRepository;
import br.com.currencyconverter.domain.transaction.usecase.FindConversionTransactionByUserIdUseCase;
import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.repository.UserRepository;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class FindConversionTransactionByUserIdAppService implements FindConversionTransactionByUserIdUseCase {
    private final ConversionTransactionRepository conversionTransactionRepository;
    private final UserRepository userRepository;

    @Override
    public List<ConversionTransactionFinded> find(UserId userId) {
        log.info("calling find(UserId) - {}", userId);

        User user = userRepository.get(userId);
        List<ConversionTransaction> transactions = conversionTransactionRepository.findByUserId(user.getId());

        return transactions.stream()
                .map(ConversionTransactionFinded::of)
                .toList();
    }
}
