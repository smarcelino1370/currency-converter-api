package br.com.currencyconverter.infra.provider.security;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.model.UserRole;
import br.com.currencyconverter.domain.user.service.UserDomainService;
import br.com.currencyconverter.infra.vo.Token;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

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
                .id(user.getId())
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
                .map(UserRole::getAuthority)
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

    public boolean validate(String token) {
        try {
            decodedToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(securityProperties.getSecretKey().getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}
