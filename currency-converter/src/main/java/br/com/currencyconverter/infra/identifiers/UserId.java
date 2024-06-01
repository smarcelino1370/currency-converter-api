package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserId userId = (UserId) object;
        return Objects.equals(id, userId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
