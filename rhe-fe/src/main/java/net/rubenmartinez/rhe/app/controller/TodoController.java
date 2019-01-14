package net.rubenmartinez.rhe.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.rubenmartinez.rhe.app.dao.domain.User;
import net.rubenmartinez.rhe.app.dto.Todo;
import net.rubenmartinez.rhe.app.service.TodoNotFoundException;
import net.rubenmartinez.rhe.app.service.TodoService;
import net.rubenmartinez.rhe.app.service.UserService;


@RestController
@RequestMapping("/api/v1/usertodos")
public class TodoController {
	
	@Autowired
	private TodoService todoService;

	@GetMapping
	public List<Todo> getTodos(Authentication authentication) {
		return todoService.findByOwnerId(getUserId(authentication));
	}
	
	@PostMapping
	public ResponseEntity<Todo> create(@RequestBody Todo todo, Authentication authentication) {
		Todo createdTodo = todoService.create(getUserId(authentication), todo);
		return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Todo> update(@PathVariable("id") Long todoId, @RequestBody Todo todoPatch, Authentication authentication) {
		checkTodoWriteAllowed(authentication, todoId);
			
		Todo updatedTodo = todoService.update(todoId, todoPatch);
		return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long todoId, Authentication authentication) {
		checkTodoWriteAllowed(authentication, todoId);
		
		todoService.delete(todoId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	private void checkTodoWriteAllowed(Authentication authentication, Long todoId) {
		Long userId = getUserId(authentication);
		if (!todoService.isOwner(getUserId(authentication), todoId)) {
			throw new UserNotAllowed(String.format("UserId [%s] is not the owner of todoId [%s]", userId, todoId));
		}
	}
	
	private static final Long getUserId(Authentication authentication) {
		return ((User) authentication.getPrincipal()).getId();
	}
}
