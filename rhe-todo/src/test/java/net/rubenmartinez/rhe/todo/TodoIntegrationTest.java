package net.rubenmartinez.rhe.todo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;


import net.rubenmartinez.rhe.todo.controller.TodoController;
import net.rubenmartinez.rhe.todo.dao.TodoRepository;
import net.rubenmartinez.rhe.todo.domain.TodoEntity;
import net.rubenmartinez.rhe.todo.dto.Todo;
import net.rubenmartinez.rhe.todo.dto.TodoNew;

/**
 * Normally we would be testing just the service or just the controller (using @MockBean for example), 
 * but this app is so simple that doing just an E2E integration will suffice to test everything.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class TodoIntegrationTest {
	
	private static final String ENDPOINT = "/api/v1/todos";
	
	private static final String PARAM_FILTER_OWNER_ID = "filterOwnerId";
	
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private TodoRepository repository;
    
    @Autowired
    private ObjectMapper jacksonObjectMapper;    

    @Test
    public void emptyDB() throws Exception {
        mvc.perform(get(ENDPOINT).contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(0)));
        
        mvc.perform(get(ENDPOINT).param(PARAM_FILTER_OWNER_ID, "1").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(0)));
    }

    
    @Test
    public void whenCreateTodoUser1_todoCreatedInDBAndReturnedViaGet() throws Exception {
    	TodoNew todo = new TodoNew();
    	todo.setCompleted(false);
    	todo.setOwnerUserId(1L);
    	todo.setTitle("First todo");
    	
    	String todoJson = jacksonObjectMapper.writeValueAsString(todo);
    	
        mvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(todoJson))
        	      .andExpect(status().isCreated());
        
        Iterable<TodoEntity> todosIter = repository.findAll();
        TodoEntity todoEntity = todosIter.iterator().next();
        
        assertEquals(todo.getOwnerUserId(), todoEntity.getOwnerUserId());
        assertEquals(todo.getTitle(), todoEntity.getTitle());

        mvc.perform(get(ENDPOINT).contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void whenCreate3TodosUser2_AllTodosReturnedViaGetForEachUser() throws Exception {
    	TodoNew todo = new TodoNew();
    	
    	todo.setCompleted(false);
    	todo.setOwnerUserId(2L);
    	todo.setTitle("First todo");
    	String todoJson = jacksonObjectMapper.writeValueAsString(todo);
        mvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(todoJson))
        	      .andExpect(status().isCreated());
        
    	todo.setTitle("Second todo");
    	todoJson = jacksonObjectMapper.writeValueAsString(todo);
        mvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(todoJson))
	      .andExpect(status().isCreated());

    	todo.setTitle("Third todo");
    	todoJson = jacksonObjectMapper.writeValueAsString(todo);
        mvc.perform(post(ENDPOINT).contentType(MediaType.APPLICATION_JSON).content(todoJson))
	      .andExpect(status().isCreated());
        
        mvc.perform(get(ENDPOINT).contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(4)));
        
        mvc.perform(get(ENDPOINT).param(PARAM_FILTER_OWNER_ID, "1").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(1)));

        mvc.perform(get(ENDPOINT).param(PARAM_FILTER_OWNER_ID, "2").contentType(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(jsonPath("$", hasSize(3)));
        
    }

}
