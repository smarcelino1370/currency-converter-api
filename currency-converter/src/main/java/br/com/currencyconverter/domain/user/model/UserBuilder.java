package br.com.currencyconverter.domain.user.model;

import br.com.currencyconverter.domain.user.model.UserRole.Role;
import br.com.currencyconverter.infra.identifiers.UserId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class UserBuilder {

    protected UserId id;
    protected String username;
    protected String password;

    private List<Role> roles = new ArrayList<>();

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }


    public UserBuilder roles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public void getRoles(Consumer<UserRole> consumer) {
        this.roles.stream().map(UserRole::from).forEach(consumer);
    }

    public User build() {
        this.id = UserId.generate();

        return new User(this);
    }
}
