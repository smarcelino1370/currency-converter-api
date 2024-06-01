package br.com.currencyconverter.infra.vo;

import br.com.currencyconverter.infra.vo.ExchangeRate.ExchangeRateBuilder;
import br.com.currencyconverter.infra.vo.ExchangeRateResponse.CurrencyWithoutExchangeRateException;
import br.com.currencyconverter.infra.vo.ExchangeRateResponse.InvalidDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeRateTest {

    private ExchangeRateBuilder builder;

    private static final Map<CurrencyUnit, BigDecimal> RATES = Map.of(
            Monetary.getCurrency("BRL"), new BigDecimal("5.6987"),
            Monetary.getCurrency("EUR"), new BigDecimal("1"),
            Monetary.getCurrency("JPY"), new BigDecimal("170.61"),
            Monetary.getCurrency("USD"), new BigDecimal("1.0850")
    );

    @BeforeEach
    void beforeEach() {
        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(
                true,
                1717189194L,
                Monetary.getCurrency("EUR"),
                LocalDate.of(2024, 5, 31),
                RATES
        );
        this.builder = ExchangeRate.builder(exchangeRateResponse);
    }

    @Test
    void mustBuildAExchangeRate() {
        ExchangeRate exchangeRate = builder.origin(Monetary.getCurrency("BRL"))
                .destination(Monetary.getCurrency("USD"))
                .build();

        assertNotNull(exchangeRate);

        assertEquals(Monetary.getCurrency("BRL"), exchangeRate.getOrigin());
        assertEquals(Monetary.getCurrency("USD"), exchangeRate.getDestination());

        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(1717189194L), ZoneOffset.UTC);
        assertEquals(localDateTime, exchangeRate.getTransactionDate());

        assertEquals(0.190394d, exchangeRate.getRate().doubleValue());

        MonetaryAmount originAmount = exchangeRate.getOriginAmount(new BigDecimal("99.99"));
        assertEquals(99.99d, originAmount.getNumber().doubleValue());
        assertEquals(Monetary.getCurrency("BRL"), originAmount.getCurrency());

        MonetaryAmount destinationAmount = exchangeRate.getDestinationAmount(new BigDecimal("99.99"));
        assertEquals(19.03749606d, destinationAmount.getNumber().doubleValue());
        assertEquals(Monetary.getCurrency("USD"), destinationAmount.getCurrency());
    }

    @Test
    void mustThrowExceptionWhenBuilderWithoutOrigin() {
        ExchangeRateBuilder builderWithoutOrigin = builder.destination(Monetary.getCurrency("USD"));
        assertThrows(NullPointerException.class, builderWithoutOrigin::build);
    }

    @Test
    void mustThrowExceptionWhenBuilderWithoutDestination() {
        ExchangeRateBuilder builderWithoutDestination = builder.origin(Monetary.getCurrency("USD"));
        assertThrows(NullPointerException.class, builderWithoutDestination::build);
    }

    @Test
    void mustThrowExceptionWhenOriginWithoutRate() {
        ExchangeRateBuilder builderOriginWithoutRate = builder
                .origin(Monetary.getCurrency("GBP"))
                .destination(Monetary.getCurrency("USD"));

        assertThrows(CurrencyWithoutExchangeRateException.class, builderOriginWithoutRate::build);
    }

    @Test
    void mustThrowExceptionWhenDestinationWithoutRate() {
        ExchangeRateBuilder builderDestinationWithoutRate = builder
                .origin(Monetary.getCurrency("BRL"))
                .destination(Monetary.getCurrency("CAD"));

        assertThrows(CurrencyWithoutExchangeRateException.class, builderDestinationWithoutRate::build);
    }

    @Test
    void mustThrowExceptionWhenExchangeRateWithoutAValidDate() {

        ExchangeRateResponse exchangeRateResponse = new ExchangeRateResponse(
                true,
                0L,
                Monetary.getCurrency("EUR"),
                LocalDate.of(2024, 5, 31),
                RATES
        );

        ExchangeRateBuilder builderWithoutAValidDate = ExchangeRate.builder(exchangeRateResponse);

        assertThrows(InvalidDateException.class, builderWithoutAValidDate::build);
    }
}
