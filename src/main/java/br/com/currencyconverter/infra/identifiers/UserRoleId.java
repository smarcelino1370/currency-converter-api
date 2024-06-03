package br.com.currencyconverter.infra.identifiers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class UserRoleId extends WrapperId<UUID> {

    @Serial
    private static final long serialVersionUID = -1558964573399317176L;

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
    public static UserRoleId from(String id) {
        return new UserRoleId(UUID.fromString(id));
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
        return new StringJoiner(", ", UserRoleId.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
