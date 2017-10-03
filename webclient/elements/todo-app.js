import {Element} from "../node_modules/@polymer/polymer/polymer-element.js"
import todoService from "../services/todo-service.js"
import "./todo-item.js"
import "./todo-input.js"
window.html = a=>a[0];

export class TodoApp extends Element {
    static get is() {
    }

    static get template() {
        return html`
        `;
    }

    constructor() {
        super();
    }
}
customElements.define(TodoApp.is, TodoApp);
