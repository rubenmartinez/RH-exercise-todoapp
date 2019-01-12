package net.rubenmartinez.rhe.user;

import org.springframework.data.repository.CrudRepository;

import net.rubenmartinez.rhe.user.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
