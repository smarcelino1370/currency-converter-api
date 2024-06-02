package br.com.currencyconverter.domain.user.app;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.repository.UserRepository;
import br.com.currencyconverter.domain.user.usecase.RegisterUserUseCase;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class RegisterUserAppService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserId handle(RegisterUser cmd) {

        log.info("calling handle(RegisterUser)");

        User user = User.builder()
                .username(cmd.getUsername())
                .password(passwordEncoder.encode(cmd.getPassword()))
                .roles(cmd.getRoles())
                .build();

        log.trace("saving user {}", user);
        userRepository.save(user);

        return user.getId();
    }
}
