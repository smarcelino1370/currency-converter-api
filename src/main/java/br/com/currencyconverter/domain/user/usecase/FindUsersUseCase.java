package br.com.currencyconverter.domain.user.usecase;

import br.com.currencyconverter.domain.user.model.User;
import br.com.currencyconverter.infra.identifiers.UserId;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public interface FindUsersUseCase {
    List<UserFinded> findAll();

    @Builder
    @Getter
    class UserFinded {

        private final UserId id;
        private final String username;

        public static UserFinded of(User us) {
            return UserFinded.builder()
                    .id(us.getId())
                    .username(us.getUsername())
                    .build();
        }
    }
}
