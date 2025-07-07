package com.todoapp.todoservice.service;

import com.todoapp.todoservice.client.UserServiceClient;
import com.todoapp.todoservice.dto.TodoRequest;
import com.todoapp.todoservice.dto.TodoResponse;
import com.todoapp.todoservice.entity.Todo;
import com.todoapp.todoservice.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    public boolean validateToken(String token) {
        try {
            return userServiceClient.validateToken(token);
        } catch (Exception e) {
            return false;
        }
    }

    public TodoResponse createTodo(TodoRequest request, Long userId) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setStatus(request.getStatus() != null ? request.getStatus() : "PENDING");
        todo.setUserId(userId);

        Todo savedTodo = todoRepository.save(todo);
        return new TodoResponse(savedTodo);
    }

    public Page<TodoResponse> getTodosByUserId(Long userId, Pageable pageable) {
        Page<Todo> todos = todoRepository.findByUserId(userId, pageable);
        return todos.map(TodoResponse::new);
    }

    public TodoResponse getTodoById(Long id, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        return new TodoResponse(todo);
    }

    public TodoResponse updateTodo(Long id, TodoRequest request, Long userId) {
        Todo todo = todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setStatus(request.getStatus());

        Todo updatedTodo = todoRepository.save(todo);
        return new TodoResponse(updatedTodo);
    }

    @Transactional
    public void deleteTodo(Long id, Long userId) {
        todoRepository.deleteByIdAndUserId(id, userId);
    }
}