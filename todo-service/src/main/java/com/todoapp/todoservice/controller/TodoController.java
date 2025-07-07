package com.todoapp.todoservice.controller;

import com.todoapp.todoservice.dto.TodoRequest;
import com.todoapp.todoservice.dto.TodoResponse;
import com.todoapp.todoservice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponse> createTodo(
            @RequestHeader("Authorization") String token,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody TodoRequest request) {
        
        if (!todoService.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TodoResponse response = todoService.createTodo(request, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<TodoResponse>> getTodos(
            @RequestHeader("Authorization") String token,
            @RequestHeader("X-User-Id") Long userId,
            Pageable pageable) {
        
        if (!todoService.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Page<TodoResponse> todos = todoService.getTodosByUserId(userId, pageable);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoResponse> getTodoById(
            @RequestHeader("Authorization") String token,
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        
        if (!todoService.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            TodoResponse response = todoService.getTodoById(id, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoResponse> updateTodo(
            @RequestHeader("Authorization") String token,
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id,
            @RequestBody TodoRequest request) {
        
        if (!todoService.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            TodoResponse response = todoService.updateTodo(id, request, userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(
            @RequestHeader("Authorization") String token,
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long id) {
        
        if (!todoService.validateToken(token.replace("Bearer ", ""))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        todoService.deleteTodo(id, userId);
        return ResponseEntity.noContent().build();
    }
}