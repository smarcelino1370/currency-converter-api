package br.com.currencyconverter.infra.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
public class Token {

    private final String username;
    private final boolean authenticated;
    private final LocalDateTime created;
    private final LocalDateTime expiresAt;
    private final String accessToken;

    private Token(TokenBuilder builder) {
        this.username = requireNonNull(builder.username);
        this.authenticated = builder.authenticated;
        this.created = requireNonNull(builder.created);
        this.expiresAt = requireNonNull(builder.expiresAt);
        this.accessToken = requireNonNull(builder.accessToken);
    }

    public static TokenBuilder builder() {
        return new TokenBuilder();
    }

    @NoArgsConstructor(access = PRIVATE)
    public static class TokenBuilder {

        private String username;
        private boolean authenticated = false;
        private LocalDateTime created;
        private LocalDateTime expiresAt;
        private String accessToken;


        public TokenBuilder username(String username) {
            this.username = username;
            return this;
        }

        public TokenBuilder authenticated() {
            this.authenticated = true;
            return this;
        }

        public TokenBuilder nonAuthenticated() {
            this.authenticated = false;
            return this;
        }

        public TokenBuilder created(Date created) {
            this.created = requireNonNull(created).toInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
            return this;
        }

        public TokenBuilder expiresAt(Date expiresAt) {
            this.expiresAt = requireNonNull(expiresAt).toInstant()
                    .atZone(ZoneOffset.UTC)
                    .toLocalDateTime();
            return this;
        }

        public TokenBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Token build() {
            return new Token(this);
        }
    }
}
