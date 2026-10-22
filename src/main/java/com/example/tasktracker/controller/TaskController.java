package com.example.tasktracker.controller;

import com.example.tasktracker.dto.TaskCreateRequest;
import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.TaskUpdateRequest;
import com.example.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для работы с задачами.
 */
@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Создать задачу.
     *
     * Пример запроса:
     * POST /api/v1/tasks
     * {
     *   "title": "Сделать пет-проект",
     *   "description": "REST API для трекера задач",
     *   "userId": 1
     * }
     */
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody TaskCreateRequest request) {
        TaskResponse response = taskService.createTask(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Получить задачу по идентификатору.
     *
     * Пример запроса:
     * GET /api/v1/tasks/1
     */
    @GetMapping("/{id}")
    public TaskResponse getTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    /**
     * Получить все задачи пользователя.
     *
     * Пример запроса:
     * GET /api/v1/tasks/user/1
     */
    @GetMapping("/user/{userId}")
    public List<TaskResponse> getTasksByUserId(@PathVariable Long userId) {
        return taskService.getTasksByUserId(userId);
    }

    /**
     * Обновить задачу.
     *
     * Пример запроса:
     * PUT /api/v1/tasks/1
     * {
     *   "title": "Сделать пет-проект",
     *   "description": "Добавить тесты",
     *   "status": "IN_PROGRESS"
     * }
     */
    @PutMapping("/{id}")
    public TaskResponse updateTask(
            @PathVariable Long id,
            @Valid @RequestBody TaskUpdateRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    /**
     * Удалить задачу.
     *
     * Пример запроса:
     * DELETE /api/v1/tasks/1
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);

        return ResponseEntity.noContent().build();
    }
}