package br.com.currencyconverter.infra.service;

import br.com.currencyconverter.domain.transaction.service.CurrencyConversionDomainService;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import br.com.currencyconverter.infra.vo.ExchangeRateResponse;
import org.springframework.stereotype.Service;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service
public class CurrencyConversionService implements CurrencyConversionDomainService {

    @Override
    public ExchangeRate handle(CurrencyUnit origin, CurrencyUnit destination) {
        return ExchangeRate.builder(get()).origin(origin).destination(destination).build();
    }

    private ExchangeRateResponse get() {
        //TODO: Implementar Lógica de Recuperar de Serviço Externo
        Map<CurrencyUnit, BigDecimal> rates = Map.of(
                Monetary.getCurrency("BRL"), new BigDecimal("5.6987"),
                Monetary.getCurrency("EUR"), new BigDecimal("1"),
                Monetary.getCurrency("JPY"), new BigDecimal("170.61"),
                Monetary.getCurrency("USD"), new BigDecimal("1.0850")
        );

        return new ExchangeRateResponse(true, 1717189194L, Monetary.getCurrency("EUR"), LocalDate.now(), rates);
    }
}
