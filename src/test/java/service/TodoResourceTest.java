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
	public final ResourceTestRule resources = ResourceTestRule.builder().addResource(new TodoResource(dao))
			.build();
	
	@Test
	public void testGetAll() {
		// Setup
		when(dao.getAllTodos()).thenReturn(Lists.newArrayList(new Todo(), new Todo(), new Todo()));
		
		// Execute
		Collection<Todo> loaded = resources.target("/todo")
				.request()
				.get()
				.readEntity(new GenericType<Collection<Todo>>() {});
		
		// Verify
		assertEquals(3, loaded.size());
	}
	
	@Test
	public void testUpdate() {
		// Setup
		Todo todo1 = new Todo();
		todo1.setId(UUID.randomUUID().toString());
		todo1.setTitle("Treat yoself");
		
		Todo edited1 = new Todo();
		edited1.setTitle("Treat yourself");
		
		Todo result = todo1.update(edited1);
		
		when(dao.updateTodo(eq(todo1.getId()), eq(edited1))).thenReturn(result);
		
		// Execute
		Todo updated = resources.target("/todo/" + todo1.getId())
				.request()
				.put(Entity.json(edited1))
				.readEntity(Todo.class);
		
		// Verify
		assertEquals("Treat yourself", updated.getTitle());
	}
}
