package net.rubenmartinez.rhe.app.user;

import org.springframework.data.repository.CrudRepository;

import net.rubenmartinez.rhe.app.user.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
