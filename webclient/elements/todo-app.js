import {Element} from "../node_modules/@polymer/polymer/polymer-element.js"
import todoService from "../services/todo-service.js"
import "./todo-item.js"
import "./todo-input.js"
top.html = a=>a[0];

export class TodoApp extends Element {
    static get is() {
        return "todo-app";
    }

    static get template() {
        return html`
            <style>
                :host {
                    --primary-color: #33c485;
                    --paper-checkbox-checked-color: #a1aba7;
                    --paper-checkbox-label-checked-color: #a1aba7;
                    display: block;
                    width: 100%;
                    height: 100%;
                    background-color: #33c485;
                    color: #323735;
                    overflow: hidden;
                    font-family: 'PT Sans', sans-serif;
                }
                .todo-wrapper {
                    background-color: white;
                    width: 300px;
                    margin: 50px auto 0 auto;
                    min-height: 350px;
                    max-height: 500px;
                    overflow: auto;
                    border-radius: 4px;
                    box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.3);
                    padding: 25px;
                }
                h1 {
                    margin: 0;
                    padding: 10px 0 25px 0;
                    text-align: center;
                }
            </style>
            <div class="todo-wrapper">
                <h1>Todo List</h1>
                <dom-repeat items="[[todos]]">
                   <template>
                       <todo-item todo="[[item]]" on-save-todo="_saveTodo" on-delete-todo="_deleteTodo"></todo-item>
                   </template>
                </dom-repeat>
                <todo-input on-create-todo="_createTodo"></todo-input>
           </div>
        `;
    }


    static get properties() {
        return [{
            todos: Array,
        }];
    }

    constructor() {
        super();
        this._loadAll();
    }

    _loadAll() {
        todoService.getAll()
            .then(todos => {
                this.todos = todos;
            });
    }

    _deleteTodo(e) {
        todoService.delete(e.model.item.id)
            .then(() => {
                this.splice("todos", e.model.index, 1);
            });
    }

    _saveTodo(e) {
        todoService.update(e.detail.todo)
            .then(todo => {
                this.set(["todos", e.model.index], todo);
            });
    }

    _createTodo(e) {
        todoService.create(e.detail)
            .then(todo => {
                this.push("todos", todo);
            });
    }
}
customElements.define(TodoApp.is, TodoApp);
