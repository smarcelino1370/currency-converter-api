package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class UserId {

    public static final String ATTRIBUTE = "id";

    @JsonValue
    private final String id;

    private UserId() {
        this.id = null;
    }

    private UserId(String id) {
        this.id = requireNonNull(id);
    }

    @JsonCreator
    public static UserId from(String id) {
        return new UserId(id);
    }

}
