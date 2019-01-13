package net.rubenmartinez.rhe.todo.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.rubenmartinez.rhe.todo.domain.TodoEntity;

public interface TodoRepository extends CrudRepository<TodoEntity, Long> {
	
    List<TodoEntity> findAllByOwnerUserId(Long ownerUserId);
    
    void deleteAllByOwnerUserId(Long ownerUserId);
}
