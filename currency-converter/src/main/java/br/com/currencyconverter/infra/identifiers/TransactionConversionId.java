package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class TransactionConversionId {

    public static final String ATTRIBUTE = "id";

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
    public static TransactionConversionId from(UUID id) {
        return new TransactionConversionId(id);
    }

}
