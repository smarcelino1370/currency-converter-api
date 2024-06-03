package br.com.currencyconverter.domain.user.repository;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.infra.identifiers.UserId;
import org.springframework.data.repository.Repository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<User, UserId> {

    void save(User user);

    default User getByUsername(String username) {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' not found!"));
    }

    Optional<User> findByUsername(String username);

    default User get(UserId userId) {
        return findById(userId).orElseThrow(userId::notFoundException);
    }

    Optional<User> findById(UserId userId);

    List<User> findAll();
}
