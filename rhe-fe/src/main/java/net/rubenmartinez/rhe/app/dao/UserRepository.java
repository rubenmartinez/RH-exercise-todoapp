package net.rubenmartinez.rhe.app.dao;

import org.springframework.data.repository.CrudRepository;

import net.rubenmartinez.rhe.app.dao.domain.User;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
}
