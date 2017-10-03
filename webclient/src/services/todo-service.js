class TodoService {
    constructor(host) {
        this.host = host + '/todo/';
        this.todos = [{
            title: "Get Milk",
            completed: false,
            id: 0
        }];
        this.count = 0;
    }

    async getAll() {
        return Promise.resolve(this.todos.slice());
    }

    async get(id) {
        let todos = this.todos.filter(todo => todo.id === id);
        return Promise.resolve(todos[0]);
    }

    async create(todo) {
        this.todos.push(todo);
        todo.id = this.count++;
        return Promise.resolve(todo);
    }

    async update(todo) {
        this
        var response = await fetch(this.host + todo.id, {
            method: 'PUT',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        });
        return response.json();
    }

    delete(id) {
        let i = 0;
        this.todos.forEach((todo, index) => {
            if(todo.id === id) {
                i = index;
            }
        });
        this.todos.splice(i, 1);
        return Promise.resolve();
    }
}

const instance = new TodoService("http://localhost:8080");

export default instance;
