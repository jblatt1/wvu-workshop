import Polymer from "../bower_components/@polymer/polymer/polymer-element.js"
import WVU from "../services/wvu.js"
import "./todo-item.js"
import "./todo-input.js"

        export class TodoApp extends Element {
            static get is() {
                return "todo-app";
            }
        
            static get template() {
                return `
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
                WVU.todoService
                    .getAll()
                    .then(todos => {
                        this.todos = todos;
                    });
            }
        
            _deleteTodo(e) {
                WVU.todoService
                    .delete(e.model.item.id)
                    .then(() => {
                        this.todos = this._removeTodoIndex(e.model.index);
                    });
            }
        
            _saveTodo(e) {
                WVU.todoService
                    .update(e.model.item)
                    .then(todo => {
                        const temp = this._removeTodoIndex(e.model.index);
                        temp.push(todo);
                        this.todos = temp;
                    });
            }
        
            _createTodo(e) {
                WVU.todoService
                    .create(e.detail)
                    .then(todo => {
                        this.todos.push(todo);
                        this.todos = this.todos.slice();
                    });
            }
        
            _removeTodoIndex(index) {
                let temp = this.todos.slice(0, index);
                temp.push(...this.todos.slice(index + 1));
                return temp;
            }
        
        }
        customElements.define(TodoApp.is, TodoApp);
