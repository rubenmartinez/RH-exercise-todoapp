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
import net.rubenmartinez.rhe.app.dto.TodoDTO;

@Service
public class TodoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

	@Autowired
    private TodoRestClient restClient;

	public List<TodoDTO> findByOwnerId(Long ownerId) {
		return restClient.findByOwnerId(ownerId).stream().map(TodoService::toAppTodo).collect(Collectors.toList());
	}
	
	public TodoDTO findOne(Long todoId) {
		return toAppTodo(restClient.findOne(todoId));
	}

	public boolean isOwner(Long userId, Long todoId) {
		RestClientTodoDTO restClientTodo = restClient.findOne(todoId);
		return restClientTodo.getOwnerUserId().equals(userId);
	}
	
	public TodoDTO create(Long userId, TodoDTO todoDTO) {
		RestClientTodoDTO restClientTodo = toRestClientTodo(todoDTO);
		restClientTodo.setOwnerUserId(userId);
		return toAppTodo(restClient.create(restClientTodo));
	}
	
	public TodoDTO update(Long todoId, TodoDTO todoPatch) {
		return toAppTodo(restClient.update(todoId, toRestClientTodo(todoPatch)));
	}
	
	public void delete(Long todoId) {
		restClient.delete(todoId);
	}

	private static final TodoDTO toAppTodo(RestClientTodoDTO restClientTodoDTO) {
		TodoDTO todoDTO = new TodoDTO();
		BeanUtils.copyProperties(restClientTodoDTO, todoDTO);
		LOGGER.trace("restClientDTOToAppDTO({}) --> {}", restClientTodoDTO, todoDTO);
		return todoDTO;
	}
	
	private static final RestClientTodoDTO toRestClientTodo(TodoDTO todoNew) {
		RestClientTodoDTO restClientTodo = new RestClientTodoDTO();
		BeanUtils.copyProperties(todoNew, restClientTodo);
		LOGGER.trace("newTodoEntity({}) --> {}", todoNew, restClientTodo);
		return restClientTodo;
	}	
}
