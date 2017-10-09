package service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.UUID;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.Rule;
import org.junit.Test;

import dao.TodoDAO;
import io.dropwizard.testing.junit.ResourceTestRule;
import jersey.repackaged.com.google.common.collect.Lists;
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
        when(dao.getAllTodos()).thenReturn(Lists.newArrayList(new Todo(), new Todo(), new Todo()));

        // Execute
        Collection<Todo> loaded = getTodos();

        // Verify
        assertEquals(3, loaded.size());
    }

    @Test
    public void testUpdate() {
        // Setup
        Todo todo = new Todo();
        todo.setId(UUID.randomUUID()
                       .toString());
        todo.setTitle("Treat yourself");

        Todo edited = new Todo();
        edited.setId(todo.getId());
        edited.setTitle("Treat yoself");

        Todo result = todo.update(edited);

        when(dao.updateTodo(eq(todo.getId()), eq(edited))).thenReturn(result);

        // Execute
        Todo updated =  updateTodo(edited);

        // Verify
        assertEquals("Treat yoself", updated.getTitle());
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
