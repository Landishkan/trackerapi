package com.example.tasktracker.service;

import com.example.tasktracker.dto.TaskCreateRequest;
import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit-тесты для TaskService.
 *
 * Здесь мы не поднимаем базу данных и Spring-контекст.
 * Вместо репозиториев используем моки.
 */
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    /**
     * Мок для репозитория задач.
     */
    @Mock
    private TaskRepository taskRepository;

    /**
     * Мок для репозитория пользователей.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * Объект, который тестируем.
     * Mockito сам подставит моки в конструктор.
     */
    @InjectMocks
    private TaskService taskService;

    /**
     * Проверяем, что задача успешно создается,
     * если пользователь существует.
     */
    @Test
    void createTask_ShouldCreateTask_WhenUserExists() {

        // Подготавливаем входные данные
        Long userId = 1L;

        TaskCreateRequest request = new TaskCreateRequest(
                "Написать тест",
                "Проверить работу сервиса задач",
                userId
        );

        User user = new User("Ирина", "irina@example.com");
        user.setId(userId);

        // Эту задачу будет возвращать мок репозитория задач
        Task savedTask = new Task();
        savedTask.setId(42L);
        savedTask.setTitle(request.title());
        savedTask.setDescription(request.description());
        savedTask.setStatus(TaskStatus.TO_DO);
        savedTask.setCreatedAt(LocalDateTime.now());
        savedTask.setUser(user);

        // Настраиваем поведение моков
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        // Вызываем метод, который тестируем
        TaskResponse response = taskService.createTask(request);

        // Проверяем результат
        assertNotNull(response);
        assertEquals(42L, response.id());
        assertEquals(request.title(), response.title());
        assertEquals(request.description(), response.description());
        assertEquals(TaskStatus.TO_DO, response.status());
        assertEquals(userId, response.userId());

        // Проверяем, что нужные методы репозиториев были вызваны
        verify(userRepository).findById(userId);
        verify(taskRepository).save(any(Task.class));
    }

    /**
     * Проверяем, что выбрасывается NotFoundException,
     * если пользователь не найден.
     */
    @Test
    void createTask_ShouldThrowNotFoundException_WhenUserDoesNotExist() {

        // Подготавливаем входные данные
        Long userId = 99L;

        TaskCreateRequest request = new TaskCreateRequest(
                "Написать тест",
                "Проверить ошибку",
                userId
        );

        // Настраиваем мок: пользователь не найден
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Проверяем, что сервис выбрасывает NotFoundException
        assertThrows(
                NotFoundException.class,
                () -> taskService.createTask(request)
        );

        // Проверяем, что сохранение задачи даже не вызывалось
        verify(taskRepository, never()).save(any(Task.class));
    }
}