package br.com.currencyconverter.infra.provider.currencyconversion;

import br.com.currencyconverter.infra.exceptionhandler.GatewayException;
import br.com.currencyconverter.infra.vo.ExchangeRate;
import br.com.currencyconverter.util.CurrencyConversionProviderUtil;
import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
class CurrencyConversionProviderTest {

    @Autowired
    private CurrencyConversionProvider currencyConversionProvider;

    @Autowired
    private CurrencyConversionProviderUtil currencyConversionProviderUtil;

    @Test
    void mustFindAExchangeRate() {
        ExchangeRate exchangeRate = currencyConversionProvider.handle(
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

    @Test
    void mustThrowGatewayExceptionWhenStatusCodeIsNotOk() {
        CurrencyConversionProvider conversionProvider = currencyConversionProviderUtil.currencyConversionService(httpClient -> {

            HttpResponse<?> response = mock(HttpResponse.class);
            doReturn(404).when(response).statusCode();
            doReturn("").when(response).body();

            try {
                doReturn(response).when(httpClient).send(any(), any());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertThrows(GatewayException.class, () -> conversionProvider.handle(
                Monetary.getCurrency("BRL"),
                Monetary.getCurrency("USD")
        ));
    }

    @Test
    void mustThrowGatewayExceptionWhenInterruptedException() {
        CurrencyConversionProvider conversionProvider = currencyConversionProviderUtil.currencyConversionService(httpClient -> {

            try {
                doThrow(InterruptedException.class).when(httpClient).send(any(), any());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertThrows(GatewayException.class, () -> conversionProvider.handle(
                Monetary.getCurrency("BRL"),
                Monetary.getCurrency("USD")
        ));
    }

    @Test
    void mustThrowGatewayExceptionWhenAnyOtherException() {
        CurrencyConversionProvider conversionProvider = currencyConversionProviderUtil.currencyConversionService(httpClient -> {

            try {
                doThrow(RuntimeException.class).when(httpClient).send(any(), any());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        assertThrows(GatewayException.class, () -> conversionProvider.handle(
                Monetary.getCurrency("BRL"),
                Monetary.getCurrency("USD")
        ));
    }
}
