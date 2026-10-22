package com.example.tasktracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание задачи.
 */
public record TaskCreateRequest(

        @NotBlank(message = "Название задачи не должно быть пустым")
        @Size(min = 3, max = 200, message = "Название задачи должно быть от 3 до 200 символов")
        String title,

        @Size(max = 1000, message = "Описание задачи не должно быть длиннее 1000 символов")
        String description,

        @NotNull(message = "Необходимо указать пользователя")
        Long userId
) {
}