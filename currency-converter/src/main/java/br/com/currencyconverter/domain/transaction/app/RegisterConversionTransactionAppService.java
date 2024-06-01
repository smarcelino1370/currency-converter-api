package br.com.currencyconverter.domain.transaction.app;

import br.com.currencyconverter.domain.transaction.model.ConversionTransaction;
import br.com.currencyconverter.domain.transaction.repository.ConversionTransactionRepository;
import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.domain.transaction.usecase.RegisterConversionTransactionUseCase;
import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterConversionTransactionAppService implements RegisterConversionTransactionUseCase {

    private final CurrencyConversionDomainService currencyConversionDomainService;
    private final ConversionTransactionRepository conversionTransactionRepository;

    @Override
    public ConversionTransactionRegistered handle(RegisterConversionTransaction cmd, UserId userId) {

        ExchangeRate exchangeRate = this.currencyConversionDomainService.handle(cmd.getOrigin(), cmd.getDestination());

        ConversionTransaction conversionTransaction = ConversionTransaction.builder()
                .userId(userId)
                .exchangeRate(exchangeRate)
                .amount(cmd.getAmount())
                .build();

        conversionTransactionRepository.save(conversionTransaction);

        return ConversionTransactionRegistered.of(conversionTransaction);
    }
}
