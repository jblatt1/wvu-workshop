package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.Rule;
import org.junit.Test;

import dao.TodoDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import model.Todo;
import resource.TodoResource;

public class TodoResourceTest {

    private final TodoDAO dao = mock(TodoDAO.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
                                                              .addResource(new TodoResource(dao))
                                                              .build();

    @Test
    public void testGetAll() {
        // Setup

        // Execute

        // Verify
        
    }

    @Test
    public void testDelete() {
        // Setup


        // Execute

        // Verify
        
    }

    // Test Helpers

    private Collection<Todo> getTodos() {
        return resources.target("/todo")
                        .request()
                        .get()
                        .readEntity(new GenericType<Collection<Todo>>() {
                        });
    }

    private Todo getTodo(String id) {
        return resources.target("/todo/" + id)
                        .request()
                        .get()
                        .readEntity(Todo.class);
    }

    private Todo createTodo(Todo todo) {
        return resources.target("/todo")
                        .request()
                        .post(Entity.json(Todo.class))
                        .readEntity(Todo.class);
    }

    private Todo updateTodo(Todo todo) {
        return resources.target("/todo/" + todo.getId())
                        .request()
                        .put(Entity.json(todo))
                        .readEntity(Todo.class);
    }

    private void deleteTodo(String id) {
        resources.target("/todo/" + id)
                 .request()
                 .delete();
    }
    
}
