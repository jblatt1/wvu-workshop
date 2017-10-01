class TodoService {
    constructor(host) {
        this.host = host + '/todo/';
    }

    async getAll() {
        var response = await fetch(this.host);
        return response.json();
    }

    async get(id) {
        var response = await fetch(this.host + id)
        return response.json();
    }

    async create(todo) {
        var response = await fetch(this.host, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        })
        return response.json();
    }

    async update(todo) {
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
        return fetch(this.host + id, {
            method: 'DELETE',
        });
    }
}

const instance = new TodoService("http://localhost:8080");

export default instance;
