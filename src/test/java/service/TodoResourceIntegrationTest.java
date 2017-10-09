package service;

import static org.junit.Assert.*;

import java.util.Collection;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;

import org.junit.ClassRule;
import org.junit.Test;

import io.dropwizard.Configuration;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import model.Todo;

public class TodoResourceIntegrationTest {

    @ClassRule
    public static final DropwizardAppRule<Configuration> RULE = new DropwizardAppRule<Configuration>(
            TodoApplication.class, ResourceHelpers.resourceFilePath("config.yaml"));

    @Test
    public void testCreateTodo() {
        // Setup
        Todo todo = new Todo();
        todo.setTitle("title");
        todo.setCompleted(true);

        // Execute
        Todo created = createTodo(todo);

        // Verify
        assertNotNull(created.getId());
        assertEquals(todo.getTitle(), created.getTitle());

        Todo loaded = getTodo(created.getId());
        assertEquals(created, loaded);
    }

    @Test
    public void testCount() {
        // Setup
        createTodo(new Todo());
        createTodo(new Todo());
        createTodo(new Todo());

        Todo createdToDelete = createTodo(new Todo());

        // Execute
        Integer count = RULE.client()
                            .target("http://localhost:8080/todo/count")
                            .request()
                            .get()
                            .readEntity(Integer.class);

        // Verify
        assertEquals(4, (int) count);

        // Delete 1 and execute again
        deleteTodo(createdToDelete.getId());
        count = RULE.client()
                    .target("http://localhost:8080/todo/count")
                    .request()
                    .get()
                    .readEntity(Integer.class);

        // Verify
        assertEquals(3, (int) count);
    }

   // Test Helpers

    private Collection<Todo> getTodos() {
        return RULE.client()
                   .target("http://localhost:8080/todo")
                   .request()
                   .get()
                   .readEntity(new GenericType<Collection<Todo>>() {
                   });
    }

    private Todo getTodo(String id) {
        return RULE.client()
                   .target("http://localhost:8080/todo/" + id)
                   .request()
                   .get()
                   .readEntity(Todo.class);
    }
    
    private Todo createTodo(Todo todo) {
        return RULE.client()
            .target("http://localhost:8080/todo")
            .request()
            .post(Entity.json(todo)).readEntity(Todo.class);
    }

    private Todo updateTodo(Todo patch) {
        return RULE.client()
                   .target("http://localhost:8080/todo/" + patch.getId())
                   .request()
                   .put(Entity.json(patch))
                   .readEntity(Todo.class);
    }

    private void deleteTodo(String id) {
        RULE.client()
            .target("http://localhost:8080/todo/" + id)
            .request()
            .delete();
    }
}
