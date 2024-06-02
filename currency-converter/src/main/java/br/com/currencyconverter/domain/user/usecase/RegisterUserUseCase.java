package br.com.currencyconverter.domain.user.usecase;

import br.com.currencyconverter.domain.user.model.UserRole.Role;
import br.com.currencyconverter.infra.identifiers.UserId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.util.List;

public interface RegisterUserUseCase {

    UserId handle(RegisterUser cmd);

    @Value
    class RegisterUser {

        @NotBlank(message = "Username must be informed!")
        String username;
        @NotNull(message = "Password must be informed!")
        String password;

        @NotEmpty(message = "Must have at least a Role informed!")
        List<Role> roles;
    }

}
