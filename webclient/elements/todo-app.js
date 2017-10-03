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
           <dom-repeat items="[[todos]]">
               <template>
                   <todo-item todo="[[item]]" on-save-todo="_saveTodo" on-delete-todo="_deleteTodo"></todo-item>
               </template>
           </dom-repeat>
           <todo-input on-create-todo="_createTodo"></todo-input>
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
