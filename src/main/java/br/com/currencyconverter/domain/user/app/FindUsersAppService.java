package br.com.currencyconverter.domain.user.app;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.domain.user.repository.UserRepository;
import br.com.currencyconverter.domain.user.usecase.FindUsersUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@RequiredArgsConstructor
@Service
public class FindUsersAppService implements FindUsersUseCase {

    private final UserRepository userRepository;

    @Override
    public List<UserFinded> findAll() {
        log.info("calling findAll()");

        List<User> users = userRepository.findAll();

        return users.stream()
                .map(UserFinded::of)
                .toList();
    }
}
