package com.example.tasktracker.dto;

import com.example.tasktracker.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Запрос на обновление задачи.
 */
public record TaskUpdateRequest(

        @NotBlank(message = "Название задачи не должно быть пустым")
        @Size(min = 3, max = 200, message = "Название задачи должно быть от 3 до 200 символов")
        String title,

        @Size(max = 1000, message = "Описание задачи не должно быть длиннее 1000 символов")
        String description,

        @NotNull(message = "Статус задачи должен быть указан")
        TaskStatus status
) {
}