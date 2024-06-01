package br.com.currencyconverter.infra.service;

import br.com.currencyconverter.infra.vo.ExchangeRate;
import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@IntegrationTest
class CurrencyConversionServiceTest {

    @Autowired
    private CurrencyConversionService currencyConversionService;

    @Test
    void mustFindAExchangeRate() {
        ExchangeRate exchangeRate = currencyConversionService.handle(
                Monetary.getCurrency("BRL"),
                Monetary.getCurrency("USD")
        );

        assertNotNull(exchangeRate);

        assertEquals(Monetary.getCurrency("BRL"), exchangeRate.getOrigin());
        assertEquals(Monetary.getCurrency("USD"), exchangeRate.getDestination());

        assertNotNull(exchangeRate.getTransactionDate());

        assertEquals(0.190625d, exchangeRate.getRate().doubleValue());

        MonetaryAmount originAmount = exchangeRate.getOriginAmount(new BigDecimal("99.99"));
        assertEquals(99.99d, originAmount.getNumber().doubleValue());
        assertEquals(Monetary.getCurrency("BRL"), originAmount.getCurrency());

        MonetaryAmount destinationAmount = exchangeRate.getDestinationAmount(new BigDecimal("99.99"));
        assertEquals(19.06059375d, destinationAmount.getNumber().doubleValue());
        assertEquals(Monetary.getCurrency("USD"), destinationAmount.getCurrency());
    }
}
