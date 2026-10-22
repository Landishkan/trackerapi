package com.example.tasktracker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Запрос на создание пользователя.
 */
public record UserCreateRequest(

        @NotBlank(message = "Имя не должно быть пустым")
        @Size(max = 100, message = "Имя не должно быть длиннее 100 символов")
        String name,

        @NotBlank(message = "Email не должен быть пустым")
        @Email(message = "Email должен быть корректным")
        @Size(max = 100, message = "Email не должен быть длиннее 100 символов")
        String email
) {
}