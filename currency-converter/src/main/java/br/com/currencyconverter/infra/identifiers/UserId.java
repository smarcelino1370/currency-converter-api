package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class UserId implements Serializable {

    @Serial
    private static final long serialVersionUID = 5540238512721688983L;

    public static final String ATTRIBUTE = "id";

    @JsonValue
    private final UUID id;

    private UserId() {
        this.id = null;
    }

    private UserId(UUID id) {
        this.id = requireNonNull(id);
    }

    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    @JsonCreator
    public static UserId from(String id) {
        return new UserId(UUID.fromString(id));
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
        return isNull(id) ? null : id.toString();
    }
}
