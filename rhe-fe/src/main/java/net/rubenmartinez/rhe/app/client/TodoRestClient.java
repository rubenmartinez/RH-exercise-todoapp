package net.rubenmartinez.rhe.app.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import net.rubenmartinez.rhe.app.client.dto.RestClientTodoDTO;


@FeignClient(name = "todo-client", path="/api/v1/todos")
public interface TodoRestClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
	RestClientTodoDTO findOne(@PathVariable("id") Long todoId);
	
	@RequestMapping(method = RequestMethod.GET, value = "/", consumes = "application/json")
	List<RestClientTodoDTO> findByOwnerId(@RequestParam(value="filterOwnerId", required=true) Long ownerUserId);

	@RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
	RestClientTodoDTO create(@RequestBody(required=true) RestClientTodoDTO todo);

	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	RestClientTodoDTO update(@PathVariable("id") Long todoId, @RequestBody(required=true) RestClientTodoDTO todoPatch);

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
	void delete(@PathVariable("id") Long todoId);
	
}
