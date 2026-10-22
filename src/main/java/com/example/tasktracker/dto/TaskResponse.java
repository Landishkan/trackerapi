package com.example.tasktracker.dto;

import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;

import java.time.LocalDateTime;

/**
 * Ответ с данными задачи.
 */
public record TaskResponse(
        Long id,
        String title,
        String description,
        TaskStatus status,
        Long userId,
        LocalDateTime createdAt
) {

    /**
     * Создает DTO из сущности задачи.
     *
     * @param task сущность задачи
     * @return DTO для ответа
     */
    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUser() != null ? task.getUser().getId() : null,
                task.getCreatedAt()
        );
    }
}