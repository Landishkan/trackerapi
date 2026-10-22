package com.example.tasktracker.dto;

import com.example.tasktracker.entity.User;

/**
 * Ответ с данными пользователя.
 */
public record UserResponse(
        Long id,
        String name,
        String email
) {

    /**
     * Создает DTO из сущности пользователя.
     *
     * @param user сущность пользователя
     * @return DTO для ответа
     */
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}