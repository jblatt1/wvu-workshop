class TodoService {
    constructor(host) {
        this.host = host + '/todo/';
    }

    getAll() {
        return fetch(this.host)
            .then(response => response.json());
    }

    get(id) {
        return fetch(this.host + id)
            .then(response => response.json());
    }

    create(todo) {
        return fetch(this.host, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        })
            .then(response => response.json());
    }

    update(todo) {
        return fetch(this.host + todo.id, {
            method: 'PUT',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        })
            .then(response => response.json());
    }

    delete(id) {
        return fetch(this.host + id, {
            method: 'DELETE',
        });
    }
}

const instance = new TodoService("http://localhost:8080")

export default instance;
