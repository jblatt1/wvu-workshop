package dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import dao.TodoDAO;
import dao.TodoDAOInMemory;
import model.Todo;

@RunWith(Parameterized.class)
public class TodoDAOTest {

    public static TodoDAO dao;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { new TodoDAOInMemory() }, { new TodoDAOMongo() } });
    }

    public TodoDAOTest(TodoDAO daoParameter) {
        dao = daoParameter;
    }

    @Test
    public void testCreateTodo() {
        // Setup
        Todo todo = new Todo();
        todo.setTitle("Show P = NP");
        todo.setCompleted(false);

        // Execute
        Todo created = dao.createTodo(todo);

        // Assert
        assertNotNull(created.getId());
        assertEquals(todo.getTitle(), created.getTitle());

        Todo loaded1 = dao.getTodo(created.getId());
        assertEquals(created, loaded1);
    }

    @Test
    public void testGetAll() {
        // Setup
        Todo todo1 = new Todo();
        todo1.setTitle("Call home");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setTitle("Get life together");
        todo2.setCompleted(true);

        dao.createTodo(todo1);
        dao.createTodo(todo2);

        // Execute
        Collection<Todo> loaded = dao.getAllTodos();

        // Assert
        assertTrue(loaded.containsAll(Arrays.asList(todo1, todo2)));
    }

    @Test
    public void testDelete() {
        // Setup
        Todo todo = new Todo();
        todo.setTitle("Get up in time to eat breakfast");
        todo.setCompleted(false);

        Todo added = dao.createTodo(todo);

        // Execute
        dao.deleteTodo(added.getId());

        // Assert
        assertFalse(dao.getAllTodos()
                       .contains(added));
    }
}
