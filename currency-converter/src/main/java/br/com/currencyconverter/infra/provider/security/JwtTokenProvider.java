package br.com.currencyconverter.infra.provider.security;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.model.UserRole;
import br.com.currencyconverter.domain.user.service.UserDomainService;
import br.com.currencyconverter.infra.vo.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RequiredArgsConstructor
@Service
public class JwtTokenProvider {

    private final SecurityProperties securityProperties;
    private final UserDomainService userDomainService;

    public Token create(User user) {

        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + securityProperties.getExpiresAt());
        var accessToken = generateAccessToken(user.getUsername(), user.getAuthorities(), now, expiresAt);

        return Token.builder()
                .username(user.getUsername())
                .authenticated()
                .created(now)
                .expiresAt(expiresAt)
                .accessToken(accessToken)
                .build();
    }

    private String generateAccessToken(String username, List<UserRole> authorities, Date now, Date expiresAt) {
        String issuerUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .build()
                .toUriString();

        List<String> roles = authorities.stream()
                .map(UserRole::getRole)
                .map(Enum::name)
                .toList();

        return JWT.create()
                .withIssuer(issuerUrl)
                .withClaim("roles", roles)
                .withSubject(username)
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(securityProperties.getSecretKey()))
                .strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDomainService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Optional<String> resolveToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .map(bearerToken -> bearerToken.substring("Bearer ".length()));
    }

    public boolean validate(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJWTAuthenticationException("Expired or invalid JWT token");
        }
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(securityProperties.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    @ResponseStatus(FORBIDDEN)
    public static class InvalidJWTAuthenticationException extends AuthenticationException {
        public InvalidJWTAuthenticationException(String ex) {
            super(ex);
        }
    }
}
