package br.com.currencyconverter.infra.hibernate.type;

import br.com.currencyconverter.infra.vo.ExchangeRate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.spi.ValueAccess;
import org.hibernate.usertype.CompositeUserType;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Objects.isNull;

public class ExchangeRateType implements CompositeUserType<ExchangeRate> {

    @Override
    public Object getPropertyValue(ExchangeRate exchangeRate, int propertyIndex) throws HibernateException {
        if (isNull(exchangeRate)) {
            return null;
        }

        return switch (propertyIndex) {
            case 0 -> exchangeRate.getDestination().getCurrencyCode();
            case 1 -> exchangeRate.getOrigin().getCurrencyCode();
            case 2 -> exchangeRate.getRate();
            case 3 -> exchangeRate.getTransactionDate();
            default -> throw new HibernateException("Invalid Property with index [ " + propertyIndex + " ]");
        };
    }

    @Override
    public ExchangeRate instantiate(ValueAccess valueAccess, SessionFactoryImplementor sessionFactoryImplementor) {
        final CurrencyUnit destination = Monetary.getCurrency(valueAccess.getValue(0, String.class));
        final CurrencyUnit origin = Monetary.getCurrency(valueAccess.getValue(1, String.class));
        final BigDecimal rate = valueAccess.getValue(2, BigDecimal.class);
        final LocalDateTime transactionDate = valueAccess.getValue(3, LocalDateTime.class);

        if (isNull(origin) && isNull(destination) && isNull(rate) && isNull(transactionDate)) {
            return null;

        }
        return new ExchangeRate(origin, destination, rate, transactionDate);

    }

    @Override
    public Class<ExchangeRateEmbeddable> embeddable() {
        return ExchangeRateEmbeddable.class;
    }

    @Override
    public Class<ExchangeRate> returnedClass() {
        return ExchangeRate.class;
    }

    @Override
    public boolean equals(ExchangeRate left, ExchangeRate right) {
        if (left == right) {
            return true;
        }

        if (isNull(left) || isNull(right)) {
            return false;
        }
        return left.equals(right);
    }

    @Override
    public int hashCode(ExchangeRate exchangeRate) {
        return exchangeRate.hashCode();
    }

    @Override
    public ExchangeRate deepCopy(ExchangeRate exchangeRate) {
        return exchangeRate;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(ExchangeRate exchangeRate) {
        return exchangeRate;
    }

    @Override
    public ExchangeRate assemble(Serializable cached, Object owner) {
        return (ExchangeRate) cached;
    }

    @Override
    public ExchangeRate replace(ExchangeRate original, ExchangeRate target, Object owner) {
        return original;
    }

    public static class ExchangeRateEmbeddable {
        private String destination;
        private String origin;
        private BigDecimal rate;
        private LocalDateTime transactionDate;
    }
}
