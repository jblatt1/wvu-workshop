import {Element} from "../node_modules/@polymer/polymer/polymer-element.js"
import "../node_modules/@polymer/paper-checkbox/paper-checkbox.js"
import "../node_modules/@polymer/paper-icon-button/paper-icon-button.js"
import "../node_modules/@polymer/iron-icons/iron-icons.js"

export class TodoItem extends Element {
    static get is() {
        return "todo-item";
    }

    static get template() {
        return html`
            <style>
                :host {
                    display: block;
                    padding: 0 0 10px 0;
                    margin-bottom: 10px;
                    font-weight: bold;
                    border-bottom: 1px solid #d9e5e0;
                    @apply(--layout-horizontal);
                    @apply(--layout-justified);
                    @apply(--layout-center);
                }
                paper-icon-button {
                    padding: 0;
                    width: 1.5em;
                    height: 1.5em;
                }
            </style>
            <paper-checkbox id="checkBox" on-checked-changed="_fire" checked="[[todo.completed]]">[[todo.title]]</paper-checkbox>
            <paper-icon-button icon="remove-circle" on-click="_delete"></paper-icon-button>
        `;
    }

    static get properties() {
        return {
            todo: {
                type: Object
            }
        };
    }

    _fire(e) {
        if (!this.isConnected) {
            return;
        }
        this.todo.completed = e.detail.value;
        this.dispatchEvent(new CustomEvent("save-todo", {
            detail: {
                todo: this.todo
            }
        }));
    }

    _delete() {
        this.dispatchEvent(new CustomEvent("delete-todo"));
    }
}
customElements.define(TodoItem.is, TodoItem);
