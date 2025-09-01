package com.yogesh.dao;
import java.time.LocalDateTime;
import java.util.List;

public class TodoDAOTest {
    public static void main(String[] args) {
        TodoDAO dao = new TodoDAO();

        try {
            // 1. Add Todo
            TodoItem todo = new TodoItem();
            todo.setTodoTitle("Learn Servlets");
            todo.setTodoDesc("Finish Servlet and JDBC integration");
            todo.setTargetDatetime(LocalDateTime.now().plusDays(1));
            todo.setTodoStatusCode("P"); // Default Pending
            todo.setCreatedBy("Admin");

            dao.addTodo(todo);

            // 2. Fetch all todos
            List<TodoItem> todos = dao.getAllTodos();
            System.out.println("All Todos:");
            for (TodoItem t : todos) {
                System.out.println(t.getTodoId() + " | " + t.getTodoTitle() + " | " + t.getTodoStatusCode());
            }

            // 3. Fetch by ID
            if (!todos.isEmpty()) {
                int firstId = todos.get(0).getTodoId();
                TodoItem fetched = dao.getTodoById(firstId);
                System.out.println("Fetched Todo: " + fetched.getTodoTitle());
            }

            // 4. Update Todo
            if (!todos.isEmpty()) {
                TodoItem toUpdate = todos.get(0);
                toUpdate.setTodoTitle("Learn Servlets & JSP");
                toUpdate.setTodoDesc("Update task with JSP learning too");
                toUpdate.setTodoStatusCode(""); // In Progress
                toUpdate.setModifiedBy("Admin");

                dao.updateTodo(toUpdate);
                System.out.println("Todo Updated Successfully!");
            }

//             5. Delete Todo
            if (!todos.isEmpty()) {
                int lastId = todos.get(todos.size() - 1).getTodoId();
                dao.deleteTodo(lastId);
                System.out.println("Deleted Todo with ID: " + lastId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
