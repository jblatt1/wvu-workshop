package resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Todo;

@Path("/todo")
@Produces(MediaType.APPLICATION_JSON)
public class TodoResource {

	private Map<String, Todo> todos = new HashMap<>();
	private Integer counter = 0;

	@GET
	public Collection<Todo> getTodos(@QueryParam("completed") Boolean completed) {
		Collection<Todo> returnTodos = this.todos.values();
		if (completed != null) {
			Collection<Todo> filteredList = new ArrayList<>();
			for(Todo each : returnTodos) {
				if(completed.equals(each.getCompleted())) {
					filteredList.add(each);
				}
			}
			returnTodos = filteredList; 
		}
		return returnTodos;
	}

	@GET
	@Path("/{id}")
	public Todo getTodo(@PathParam("id") String id) {
		return this.todos.get(id);
	}

	@POST
	public Todo createTodo(Todo todo) {
		this.counter++;
		String id = counter.toString();
		todo.setId(id);
		todo.setCompleted(false);
		this.todos.put(id.toString(), todo);
		return todo;
	}

	@PUT
	@Path("{id}")
	public Todo updateTodo(@PathParam("id") String id, Todo patch) {
		Todo oldTodo = this.todos.get(id);
		Todo updated = oldTodo.update(patch);
		this.todos.put(id, updated);
		return updated;
	}

	@DELETE
	@Path("{id}")
	public void deleteTodo(@PathParam("id") String id) {
		this.todos.remove(id);
	}
}
