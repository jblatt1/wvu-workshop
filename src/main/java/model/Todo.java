package model;

public class Todo {

	private String title;
	private Boolean completed;
	private String id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getCompleted() {
		return completed;
	}

	public void setCompleted(Boolean completed) {
		this.completed = completed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Todo update(Todo todo) {
		if (todo.getTitle() != null) {
			this.title = todo.getTitle();
		}
		if (todo.getCompleted() != null) {
			this.completed = todo.getCompleted();
		}
		
		return this;
	}

}
