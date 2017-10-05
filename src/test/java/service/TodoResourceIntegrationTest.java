package service;

import static org.junit.Assert.*;

import javax.ws.rs.client.Entity;

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
        Todo created = RULE.client()
                           .target("http://localhost:8080/todo/")
                           .request()
                           .post(Entity.json(todo))
                           .readEntity(Todo.class);

        // Verify
        assertEquals(todo.getTitle(), created.getTitle());

        Todo loaded = RULE.client()
                          .target("http://localhost:8080/todo/" + created.getId())
                          .request()
                          .get()
                          .readEntity(Todo.class);
        assertEquals(created, loaded);
    }

    @Test
    public void testCount() {
        // Setup
        createTodo();
        createTodo();
        createTodo();

        Todo created = RULE.client()
                           .target("http://localhost:8080/todo")
                           .request()
                           .post(Entity.json(new Todo()))
                           .readEntity(Todo.class);

        // Execute
        Integer count = RULE.client()
                            .target("http://localhost:8080/todo/count")
                            .request()
                            .get()
                            .readEntity(Integer.class);

        // Verify
        assertEquals(4, (int) count);

        // Delete 1 and execute again
        RULE.client()
            .target("http://localhost:8080/todo/" + created.getId())
            .request()
            .delete();
        count = RULE.client()
                    .target("http://localhost:8080/todo/count")
                    .request()
                    .get()
                    .readEntity(Integer.class);

        // Verify
        assertEquals(3, (int) count);
    }

    private void createTodo() {
        RULE.client()
            .target("http://localhost:8080/todo")
            .request()
            .post(Entity.json(new Todo()));
    }
}
