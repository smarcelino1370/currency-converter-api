package br.com.currencyconverter.infra.identifiers;

import br.com.currencyconverter.infra.exceptionhandler.NotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

@Getter
@Embeddable
public class UserId extends WrapperId<UUID> {

    @Serial
    private static final long serialVersionUID = 5540238512721688983L;

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
        return new StringJoiner(", ", UserId.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }

    public UserNotFoundException notFoundException() {
        return new UserNotFoundException(this.asString());
    }

    public static class UserNotFoundException extends NotFoundException {
        public UserNotFoundException(String id) {
            super("User not found with id: " + id);
        }
    }
}
