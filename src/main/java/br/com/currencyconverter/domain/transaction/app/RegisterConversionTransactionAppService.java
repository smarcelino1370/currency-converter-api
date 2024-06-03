package br.com.currencyconverter.domain.transaction.app;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.domain.transaction.repository.ConversionTransactionRepository;
import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.repository.UserRepository;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class RegisterConversionTransactionAppService implements RegisterConversionTransactionUseCase {

    private final CurrencyConversionDomainService currencyConversionDomainService;
    private final ConversionTransactionRepository conversionTransactionRepository;
    private final UserRepository userRepository;

    @Override
    public ConversionTransactionRegistered handle(RegisterConversionTransaction cmd, UserId userId) {

        log.info("calling handle(RegisterConversionTransaction, UserId) for UserId {}", userId);

        User user = userRepository.get(userId);
        ExchangeRate exchangeRate = this.currencyConversionDomainService.handle(cmd.getOrigin().getCurrencyUnit(), cmd.getDestination().getCurrencyUnit());

        ConversionTransaction conversionTransaction = ConversionTransaction.builder()
                .userId(user.getId())
                .exchangeRate(exchangeRate)
                .amount(cmd.getAmount())
                .build();

        log.trace("saving conversionTransaction {}", conversionTransaction);
        conversionTransactionRepository.save(conversionTransaction);

        return ConversionTransactionRegistered.of(conversionTransaction);
    }
}
