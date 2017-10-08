class TodoService {
    constructor(host) {
        this.host = host + '/api/todo/';
    }

    getAll() {
        return fetch(this.host).then(response => response.json());
    }

    create(todo) {
        return fetch(this.host, {
            method: 'POST',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        }).then(response => response.json());
    }

    update(todo) {
        return fetch(this.host + todo.id, {
            method: 'PUT',
            mode: 'cors',
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(todo)
        }).then(response => response.json());
    }

    delete(id) {
        return fetch(this.host + id, {
            method: "DELETE",
            mode: 'cors',
        });
    }
}

const instance = new TodoService("");

export default instance;
