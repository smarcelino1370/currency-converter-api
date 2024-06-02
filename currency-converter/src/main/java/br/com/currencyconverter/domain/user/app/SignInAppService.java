package br.com.currencyconverter.domain.user.app;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.usecase.SignInUseCase;
import br.com.currencyconverter.infra.provider.security.JwtTokenProvider;
import br.com.currencyconverter.infra.vo.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class SignInAppService implements SignInUseCase {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Token handle(SignIn cmd) {
        Authentication authentication = authenticationManager.authenticate(cmd.authentication());
        User user = (User) authentication.getPrincipal();
        return jwtTokenProvider.create(user);
    }
}
