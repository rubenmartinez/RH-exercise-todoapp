package net.rubenmartinez.rhe.todo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.rubenmartinez.rhe.todo.dao.TodoRepository;
import net.rubenmartinez.rhe.todo.domain.TodoEntity;
import net.rubenmartinez.rhe.todo.dto.Todo;
import net.rubenmartinez.rhe.todo.dto.TodoNew;
import net.rubenmartinez.rhe.todo.dto.TodoPatch;

@Service
public class TodoService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TodoService.class);

	
    @Autowired
    private TodoRepository repository;

	public List<Todo> findAll() {
		List<Todo> todoList = stream(repository.findAll()).map(TodoService::entityToDTO).collect(Collectors.toList());
		LOGGER.debug("findAll() returned {} elements", todoList.size());
		return todoList;
	}

	public List<Todo> findByOwnerId(Long ownerId) {
		List<Todo> todoList = stream(repository.findAllByOwnerUserId(ownerId)).map(TodoService::entityToDTO).collect(Collectors.toList());
		LOGGER.debug("findByOwnerId({}) returned {} elements", ownerId, todoList.size());
		return todoList;
	}
	
	public Todo findOne(Long todoId) {
		Todo todo = entityToDTO(findMandatory(todoId));
		LOGGER.debug("findOne({}) --> {}", todoId, todo);
		return todo;
	}
	
	public Todo update(Long todoId, TodoPatch todoPatch) {
		TodoEntity todoEntity = findMandatory(todoId);
		
		todoPatch.getTitle().ifPresent( title -> todoEntity.setTitle(title) );
		todoPatch.getOwnerUserId().ifPresent( ownerId -> todoEntity.setOwnerUserId(ownerId) );
		todoPatch.getCompleted().ifPresent( completed -> todoEntity.setCompleted(completed) );
		
		TodoEntity todoEntityUpdated = repository.save(todoEntity);
		LOGGER.debug("update({}, {}) --> {}", todoId, todoPatch, todoEntityUpdated);
		return entityToDTO(todoEntityUpdated);
	}
	
	public Todo create(TodoNew todo) {
		TodoEntity todoEntity = repository.save(newTodoEntity(todo));
		LOGGER.debug("create({}) --> {}", todo, todoEntity);
		return entityToDTO(todoEntity);
	}

	public void delete(Long todoId) {
		TodoEntity todoEntity = findMandatory(todoId);
		repository.deleteById(todoId);
		LOGGER.debug("delete({}). Deleted entity is: {}", todoId, todoEntity);
	}
	
	private TodoEntity findMandatory(Long todoId) {
		Optional<TodoEntity> todoEntity = repository.findById(todoId);
		return todoEntity.orElseThrow(
				() -> new TodoNotFoundException("Todo id [" + todoId + "] not found"));
	}

	private static final <T> Stream<T> stream(Iterable<T> iterable) {
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	
	private static final Todo entityToDTO(TodoEntity todoEntity) {
		Todo todo = new Todo();
		BeanUtils.copyProperties(todoEntity, todo);
		LOGGER.trace("entityToDTO({}) --> {}", todoEntity, todo);
		return todo;
	}
	
	private static final TodoEntity newTodoEntity(TodoNew todoNew) {
		TodoEntity todoEntity = new TodoEntity();
		BeanUtils.copyProperties(todoNew, todoEntity);
		LOGGER.trace("newTodoEntity({}) --> {}", todoNew, todoEntity);
		return todoEntity;
	}
	
}
