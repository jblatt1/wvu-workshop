import {Element} from "../node_modules/@polymer/polymer/polymer-element.js"
import {WVU} from "../services/wvu.js"
import "./todo-item.js"
import "./todo-input.js"

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

    async _loadAll() {
        this.todos = await WVU.todoService.getAll();
    }

    async _deleteTodo(e) {
        await WVU.todoService.delete(e.model.item.id);
        this.splice("todos", e.model.index, 1);
    }

    async _saveTodo(e) {
        this.set(["todos", e.model.index], await WVU.todoService.update(e.model.item));
    }

    async _createTodo(e) {
        this.push("todos", await WVU.todoService.create(e.detail));
    }
}
customElements.define(TodoApp.is, TodoApp);
