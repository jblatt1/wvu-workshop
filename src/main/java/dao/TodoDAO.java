package dao;

import java.util.Collection;

import model.Todo;

public interface TodoDAO {

	Collection<Todo> getAllTodos();

	Collection<Todo> getTodoByCompleted(Boolean completed);

	Todo getTodo(String id);

	Todo createTodo(Todo todo);

	Todo updateTodo(String id, Todo todo);

	void deleteTodo(String id);
	
	Integer getTodoCount();
}
