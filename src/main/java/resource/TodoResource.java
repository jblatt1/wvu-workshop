package resource;

import java.util.Collection;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import dao.TodoDAO;
import model.Todo;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {
	private final TodoDAO dao;

	public TodoResource(TodoDAO dao) {
		this.dao = dao;
	}

	@GET
	public Collection<Todo> getTodos(@QueryParam("completed") Boolean completed) {
		Collection<Todo> returnValue;
		if (completed != null) {
			returnValue = this.dao.getTodoByCompleted(completed);
		} else {
			returnValue = this.dao.getAllTodos();
		}
		return returnValue;
	}

	@GET
	@Path("/{id}")
	public Todo getTodo(@PathParam("id") String id) {
		return this.dao.getTodo(id);
	}

	@POST
	public Todo createTodo(Todo todo) {
		return this.dao.createTodo(todo);
	}

	@PUT
	@Path("{id}")
	public Todo updateTodo(@PathParam("id") String id, Todo patch) {
		return this.dao.updateTodo(id, patch);
	}

	@DELETE
	@Path("/{id}")
	public void deleteTodo(@PathParam("id") String id) {
		this.dao.deleteTodo(id);
	}
	
	@GET
	@Path("/count")
	public Integer getTodoCount() {
		return this.dao.getTodoCount();
	}

}
