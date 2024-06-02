package br.com.currencyconverter.domain.user.usecase;

import br.com.currencyconverter.infra.vo.Token;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public interface SignInUseCase {

    Token handle(SignIn cmd);

    @Value
    class SignIn {

        @NotBlank(message = "Username must be informed!")
        String username;
        @NotNull(message = "Password must be informed!")
        String password;

        public UsernamePasswordAuthenticationToken authentication() {
            return new UsernamePasswordAuthenticationToken(username, password);
        }
    }
}
