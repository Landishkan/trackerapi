package com.example.tasktracker.repository;

import com.example.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий для работы с пользователями.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Проверяет, существует ли пользователь с таким email.
     *
     * @param email email пользователя
     * @return true, если пользователь с таким email уже есть
     */
    boolean existsByEmail(String email);
}