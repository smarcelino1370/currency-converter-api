package br.com.currencyconverter.domain.user.model;

import br.com.currencyconverter.infra.identifiers.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
@NoArgsConstructor(access = PRIVATE, force = true)
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @EmbeddedId
    @AttributeOverride(name = UserId.ATTRIBUTE, column = @Column(name = "id"))
    private final UserId id;

    private final String username;

    private final String password;

    @OneToMany(
            cascade = ALL,
            fetch = EAGER,
            orphanRemoval = true
    )
    @JoinColumn(name = "user_id")
    private final List<UserRole> authorities = new ArrayList<>();

    User(UserBuilder builder) {
        this.id = requireNonNull(builder.id);
        this.username = requireNonNull(builder.username);
        this.password = requireNonNull(builder.password);

        builder.getRoles(authorities::add);
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }
}
