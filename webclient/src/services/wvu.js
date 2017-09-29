import TodoService from "./todo-service.js"

export const  WVU = {
    todoService: new TodoService("http://localhost:8080")
};
