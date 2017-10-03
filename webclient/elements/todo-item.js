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
                    padding: 1rem;
                    @apply(--layout-horizontal);
                    @apply(--layout-justified);
                    @apply(--layout-center);
                }
            </style>

            <!--
            <paper-checkbox id="checkBox" on-checked-changed="_fire" checked="[[todo.completed]]">[[todo.title]]</paper-checkbox>
            <paper-icon-button icon="delete" on-click="_delete"></paper-icon-button>
            -->
        `;
    }

    static get properties() {
        return {
        };
    }
}
customElements.define(TodoItem.is, TodoItem);
