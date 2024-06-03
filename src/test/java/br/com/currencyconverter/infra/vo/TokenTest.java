package br.com.currencyconverter.infra.vo;

import br.com.currencyconverter.infra.identifiers.UserId;
import br.com.currencyconverter.infra.vo.Token.TokenBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static java.time.ZoneOffset.UTC;
import static org.junit.jupiter.api.Assertions.*;

@Tag("ut")
class TokenTest {

    private final Date DATE = new Date();
    private TokenBuilder builder;

    @BeforeEach
    void beforeEach() {
        this.builder = Token.builder()
                .id(UserId.generate())
                .username("user")
                .authenticated()
                .created(DATE)
                .expiresAt(DATE)
                .accessToken("access_token");
    }

    @Test
    void mustBuildATokenAuthenticated() {
        Token token = this.builder.build();

        assertNotNull(token);
        assertEquals("user", token.getUsername());
        assertTrue(token.isAuthenticated());
        assertEquals(DATE.toInstant(), token.getCreated().toInstant(UTC));
        assertEquals(DATE.toInstant(), token.getExpiresAt().toInstant(UTC));
        assertEquals("access_token", token.getAccessToken());
    }

    @Test
    void mustBuildATokenNonAuthenticated() {
        Token token = this.builder.nonAuthenticated()
                .build();

        assertNotNull(token);
        assertEquals("user", token.getUsername());
        assertFalse(token.isAuthenticated());
        assertEquals(DATE.toInstant(), token.getCreated().toInstant(UTC));
        assertEquals(DATE.toInstant(), token.getExpiresAt().toInstant(UTC));
        assertEquals("access_token", token.getAccessToken());
    }

    @Test
    void mustThrowExceptionWhenWithoutUsername() {
        TokenBuilder tokenBuilder = this.builder.username(null);

        assertThrows(NullPointerException.class, tokenBuilder::build);
    }

    @Test
    void mustThrowExceptionWhenWithoutCreated() {
        assertThrows(NullPointerException.class, () -> this.builder.created(null));
    }

    @Test
    void mustThrowExceptionWhenWithoutExpiresAt() {
        assertThrows(NullPointerException.class, () -> this.builder.expiresAt(null));
    }

    @Test
    void mustThrowExceptionWhenWithoutAccessToken() {
        TokenBuilder tokenBuilder = this.builder.accessToken(null);

        assertThrows(NullPointerException.class, tokenBuilder::build);
    }
}
