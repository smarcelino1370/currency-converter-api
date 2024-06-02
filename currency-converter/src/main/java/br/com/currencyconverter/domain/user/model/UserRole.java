package br.com.currencyconverter.domain.user.model;

import br.com.currencyconverter.infra.identifiers.UserRoleId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.StringJoiner;

import static jakarta.persistence.EnumType.STRING;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@RequiredArgsConstructor
@Entity
@Table(name = "user_role")
public class UserRole implements GrantedAuthority {

    @EmbeddedId
    @AttributeOverride(name = UserRoleId.ATTRIBUTE, column = @Column(name = "id"))
    private final UserRoleId id;

    @Enumerated(STRING)
    private final Role role;

    public static UserRole from(Role role) {
        return new UserRole(UserRoleId.generate(), requireNonNull(role));
    }

    @Override
    public String getAuthority() {
        return requireNonNull(role).name();
    }

    public enum Role {
        ADMIN
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserRole userRole = (UserRole) object;
        return Objects.equals(id, userRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRole.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("role=" + role)
                .toString();
    }
}
