import {Element} from "../node_modules/@polymer/polymer/polymer-element.js"
import "../node_modules/@polymer/iron-flex-layout/iron-flex-layout.js"
import "../node_modules/@polymer/paper-button/paper-button.js"
import "../node_modules/@polymer/paper-checkbox/paper-checkbox.js"
import "../node_modules/@polymer/paper-input/paper-input.js"

export class TodoInput extends Element {
    static get is() {
        return "todo-input";
    }

    static get template() {
        return html`
            <style>
                :host {
                    @apply(--layout-horizontal);
                    @apply(--layout-center);
                }
                paper-button {
                    --paper-button: {
                        background-color: #33c485;
                        color: white;
                        font-weight: bold;
                        padding: 5px;
                        margin-left: 20px;
                        border-radius: 4px;
                        text-transform: none;
                        font-family: 'PT Sans', sans-serif;
                    }   
                }
            </style>
            <paper-checkbox id="checkBox" checked="{{completed}}"></paper-checkbox>
            <paper-input no-label-float value="{{title}}">Title</paper-input>
            <paper-button on-click="_save">Save</paper-button>
        `;

    }

    static get properties() {
        return {
            title: String,
            completed: Boolean
        };
    }

    _save() {
        this.dispatchEvent(new CustomEvent("create-todo", {
            detail: {
                title: this.title,
                completed: this.completed
            }
        }));
        this.title = "";
        this.completed = false;
    }
}
customElements.define(TodoInput.is, TodoInput);
