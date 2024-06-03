package br.com.currencyconverter.infra.vo;

import br.com.currencyconverter.infra.vo.ExchangeRate.ExchangeRateBuilder;
import br.com.currencyconverter.infra.vo.ExchangeRate.ExchangeRateBuilder.CurrencyWithoutExchangeRateException;
import br.com.currencyconverter.infra.vo.ExchangeRate.ExchangeRateBuilder.InvalidDateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Tag("ut")
class ExchangeRateTest {

    private static final Map<String, BigDecimal> RATES = Map.of(
            "BRL", new BigDecimal("5.6987"),
            "EUR", new BigDecimal("1"),
            "JPY", new BigDecimal("170.61"),
            "USD", new BigDecimal("1.0850")
    );
    private ExchangeRateBuilder builder;

    @BeforeEach
    void beforeEach() {
        this.builder = ExchangeRate.builder()
                .timestamp(1717189194L)
                .rates(RATES);
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

        ExchangeRateBuilder builderWithoutAValidDate = ExchangeRate.builder()
                .timestamp(0L)
                .rates(RATES)
                .origin(Monetary.getCurrency("BRL"))
                .destination(Monetary.getCurrency("USD"));

        assertThrows(InvalidDateException.class, builderWithoutAValidDate::build);
    }

    @Test
    void mustThrowExceptionWhenExchangeRateWithoutRates() {

        ExchangeRateBuilder builderWithoutAValidDate = ExchangeRate.builder()
                .timestamp(1717189194L)
                .rates(null)
                .origin(Monetary.getCurrency("BRL"))
                .destination(Monetary.getCurrency("USD"));

        assertThrows(CurrencyWithoutExchangeRateException.class, builderWithoutAValidDate::build);
    }
}
