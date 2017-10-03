package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Todo;

public class TodoDAOInMemory implements TodoDAO {

	private Map<String, Todo> todos = new HashMap<>();
	private Integer counter = 0;

	@Override
	public Collection<Todo> getAllTodos() {
		return this.todos.values();
	}

	@Override
	public Collection<Todo> getTodoByCompleted(Boolean completed) {
		Collection<Todo> allValues = this.todos.values();
		List<Todo> filtered = new ArrayList<>();
		for (Todo todo : allValues) {
			if (completed.equals(todo.getCompleted())) {
				filtered.add(todo);
			}
		}
		return filtered;
	}

	@Override
	public Todo getTodo(String id) {
		return this.todos.get(id);
	}

	@Override
	public Todo createTodo(Todo todo) {
		this.counter++;
		String id = this.counter.toString();
		todo.setId(id);
		todo.setCompleted(false);
		this.todos.put(id, todo);
		return todo;
	}

	@Override
	public Todo updateTodo(String id, Todo todo) {
		Todo toUpdate = this.todos.get(id);
		Todo updated = toUpdate.update(todo);
		updated = this.todos.put(id, updated);
		return updated;
	}

	@Override
	public void deleteTodo(String id) {
		this.todos.remove(id);
	}
	
	@Override
	public Integer getTodoCount() {
		return this.todos.size();
	}

}
