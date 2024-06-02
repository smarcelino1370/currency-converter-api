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
public class UserRoleId implements Serializable {

    @Serial
    private static final long serialVersionUID = -1558964573399317176L;

    public static final String ATTRIBUTE = "id";

    @JsonValue
    private final UUID id;

    private UserRoleId() {
        this.id = null;
    }

    private UserRoleId(UUID id) {
        this.id = requireNonNull(id);
    }

    public static UserRoleId generate() {
        return new UserRoleId(UUID.randomUUID());
    }

    @JsonCreator
    public static UserRoleId from(UUID id) {
        return new UserRoleId(id);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserRoleId userId = (UserRoleId) object;
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
