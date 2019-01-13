package net.rubenmartinez.rhe.todo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.rubenmartinez.rhe.todo.dto.Todo;
import net.rubenmartinez.rhe.todo.dto.TodoNew;
import net.rubenmartinez.rhe.todo.dto.TodoPatch;
import net.rubenmartinez.rhe.todo.service.TodoNotFoundException;
import net.rubenmartinez.rhe.todo.service.TodoService;


@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@GetMapping
	public List<Todo> findAll(@RequestParam(value = "filterOwnerId", required = false) Optional<Long> ownerId) {
		if (ownerId.isPresent()) {
			return todoService.findByOwnerId(ownerId.get());
		}
		else {
			return todoService.findAll();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Todo> findOne(@PathVariable("id") Long todoId) {
		try {
			Todo todo = todoService.findOne(todoId);
			return ResponseEntity.ok().body(todo);
		} catch (TodoNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping
	public ResponseEntity<Todo> create(@RequestBody TodoNew todo) {
		Todo createdTodo = todoService.create(todo);
		return new ResponseEntity<>(createdTodo, HttpStatus.CREATED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<Todo> update(@PathVariable("id") Long todoId, @RequestBody TodoPatch todo) {
		Todo udpateTodo = todoService.update(todoId, todo);
		return new ResponseEntity<>(udpateTodo, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long todoId) {
		try {
			todoService.delete(todoId);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (TodoNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
