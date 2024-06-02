package br.com.currencyconverter.infra.provider.security;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.service.UserDomainService;
import br.com.currencyconverter.infra.vo.Token;
import br.com.currencyconverter.util.IntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import java.util.List;

import static br.com.currencyconverter.domain.user.model.UserRole.Role.ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@IntegrationTest
class JwtTokenProviderTest {

    @Mock
    private UserDomainService userDomainService;

    @Autowired
    private SecurityProperties securityProperties;

    private JwtTokenProvider jwtTokenProvider;

    private User user;

    private static final String EXPIRED_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0Iiwicm9sZXMiOlsiQURNSU4iXSwic3ViIjoiaXQiLCJpYXQiOjE2OTk1MjQwMDAsImV4cCI6MTY5OTUyNzYwMH0.uiJ3mv1qk0zZLm0Sw0AbXBYpH5zlsM0jYl-8sAPbOaQ";

    @BeforeEach
    void beforeEach() {
        MockitoAnnotations.openMocks(this);

        this.user = User.builder()
                .username("it")
                .password("it")
                .roles(List.of(ADMIN))
                .build();

        when(userDomainService.loadUserByUsername(any())).thenReturn(this.user);

        this.jwtTokenProvider = new JwtTokenProvider(securityProperties, userDomainService);
    }

    @Test
    void createAValidToken() {

        Token token = jwtTokenProvider.create(this.user);
        assertNotNull(token);

        assertTrue(jwtTokenProvider.validate(token.getAccessToken()));

        Authentication authentication = jwtTokenProvider.getAuthentication(token.getAccessToken());
        assertNotNull(authentication);
    }

    @Test
    void mustThrowExceptionWhenExpiredToken() {
        assertFalse(jwtTokenProvider.validate(EXPIRED_TOKEN));
    }

    @Test
    void mustThrowExceptionWhenInvalidToken() {
        assertFalse(jwtTokenProvider.validate("this.is.not.a.access.token"));
    }
}
