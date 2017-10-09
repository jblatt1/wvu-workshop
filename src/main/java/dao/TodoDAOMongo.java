package dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;

import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import model.Todo;

public class TodoDAOMongo implements TodoDAO {

    private final String HOST = "localhost";
    private final int PORT = 27017;
    private final String DB_NAME = "todo-db";
    private final String COLLECTION_NAME = "todos";

    private MongoClient mongoClient;
    private MongoCollection<Document> collection;

    private int counter = 0;

    private ObjectMapper mapper = new ObjectMapper();

    public TodoDAOMongo() {
        this.mongoClient = new MongoClient(new ServerAddress(this.HOST, this.PORT));
        MongoDatabase db = this.mongoClient.getDatabase(DB_NAME);
        this.collection = db.getCollection(COLLECTION_NAME);
        this.collection.deleteMany(new Document());
    }

    @Override
    public Collection<Todo> getAllTodos() {
        Collection<Todo> loaded = new ArrayList<>();
        this.collection.find()
                       .map(document -> {
                           document.remove("_id");
                           return document;
                       })
                       .iterator()
                       .forEachRemaining(document -> loaded.add(
                               wrapException(() -> mapper.readValue(document.toJson(), Todo.class))));
        return loaded;
    }

    @Override
    public Collection<Todo> getTodoByCompleted(Boolean completed) {
        Collection<Todo> loaded = new ArrayList<>();
        this.collection.find(Filters.eq("completed", completed))
                       .map(document -> {
                           document.remove("_id");
                           return document;
                       })
                       .iterator()
                       .forEachRemaining(document -> loaded.add(
                               wrapException(() -> mapper.readValue(document.toJson(), Todo.class))));
        return loaded;
    }

    @Override
    public Todo getTodo(String id) {
        Document todo = this.collection.find(Filters.eq("id", id))
                                       .first();
        todo.remove("_id");
        return wrapException(() -> mapper.readValue(todo.toJson(), Todo.class));
    }

    @Override
    public Todo createTodo(Todo todo) {
        todo.setId("" + counter);
        counter++;
        collection.insertOne(Document.parse(wrapException(() -> mapper.writeValueAsString(todo))));
        return todo;
    }

    @Override
    public Todo updateTodo(String id, Todo todo) {
        Todo toUpdate = getTodo(id);
        Todo updated = toUpdate.update(todo);
        this.collection.replaceOne(Filters.eq("id", id),
                Document.parse(wrapException(() -> mapper.writeValueAsString(updated))));
        return updated;
    }

    @Override
    public void deleteTodo(String id) {
        this.collection.deleteOne(Filters.eq("id", id));
    }

    @Override
    public Integer getTodoCount() {
        return (int) collection.count();
    }

    // Helper Method to Wrap Mapper's Exceptionss
    private <T> T wrapException(Callable<T> callable) {
        try {
            System.out.println(callable.call());
            return callable.call();
        } catch (Exception e) {
            throw new IllegalStateException("Parsing failed.");
        }
    }
}
