package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class TransactionConversionId extends WrapperId<UUID> {

    @Serial
    private static final long serialVersionUID = 7539620175259666485L;

    @JsonValue
    private final UUID id;

    private TransactionConversionId() {
        this.id = null;
    }

    private TransactionConversionId(UUID id) {
        this.id = requireNonNull(id);
    }

    public static TransactionConversionId generate() {
        return new TransactionConversionId(UUID.randomUUID());
    }

    @JsonCreator
    public static TransactionConversionId from(String id) {
        return new TransactionConversionId(UUID.fromString(id));
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TransactionConversionId that = (TransactionConversionId) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
