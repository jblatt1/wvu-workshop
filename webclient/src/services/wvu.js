<script type="module">
    import TodoService from "./todo-service.js"
    window.WVU = {
        todoService: new TodoService("http://localhost:8080")
    };
</script>
