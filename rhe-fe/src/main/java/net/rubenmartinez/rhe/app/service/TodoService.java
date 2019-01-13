package net.rubenmartinez.rhe.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rubenmartinez.rhe.app.client.TodoRestClient;
import net.rubenmartinez.rhe.app.client.dto.RestClientTodoDTO;
import net.rubenmartinez.rhe.app.dto.Todo;

@Service
public class TodoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

	@Autowired
    private TodoRestClient restClient;

	public List<Todo> findByOwnerId(Long ownerId) {
		return restClient.findByOwnerId(ownerId).stream().map(TodoService::toAppTodo).collect(Collectors.toList());
	}
	
	public Todo findOne(Long todoId) {
		return toAppTodo(restClient.findOne(todoId));
	}

	public boolean isOwner(Long userId, Long todoId) {
		RestClientTodoDTO restClientTodo = restClient.findOne(todoId);
		return restClientTodo.getOwnerUserId().equals(userId);
	}
	
	public void create(Todo todo) {
		restClient.create(toRestClientTodo(todo));
	}
	
	public void update(Long todoId, Todo todoPatch) {
		restClient.update(todoId, toRestClientTodo(todoPatch));
	}
	
	public void delete(Long todoId) {
		restClient.delete(todoId);
	}

	private static final Todo toAppTodo(RestClientTodoDTO restClientTodoDTO) {
		Todo todo = new Todo();
		BeanUtils.copyProperties(restClientTodoDTO, todo);
		LOGGER.trace("restClientDTOToAppDTO({}) --> {}", restClientTodoDTO, todo);
		return todo;
	}
	
	private static final RestClientTodoDTO toRestClientTodo(Todo todoNew) {
		RestClientTodoDTO restClientTodo = new RestClientTodoDTO();
		BeanUtils.copyProperties(todoNew, restClientTodo);
		LOGGER.trace("newTodoEntity({}) --> {}", todoNew, restClientTodo);
		return restClientTodo;
	}	
}
