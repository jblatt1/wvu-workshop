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
       

        // Execute

        // Assert
        
    }

    @Test
    public void testGet() {
        // Setup

        // Execute

        // Assert
        
    }

    @Test
    public void testDelete() {
        // Setup

        // Execute

        // Assert
         
    }
}
