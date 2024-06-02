package br.com.currencyconverter.infra.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

import static java.util.Objects.requireNonNull;
import static lombok.AccessLevel.PRIVATE;

@Getter
public class Token {

    private final String username;
    private final Boolean authenticated;
    private final Date created;
    private final Date expiresAt;
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
        private Date created;
        private Date expiresAt;
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
            this.created = created;
            return this;
        }

        public TokenBuilder expiresAt(Date expiresAt) {
            this.expiresAt = expiresAt;
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
