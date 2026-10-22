package com.example.tasktracker.service;

import com.example.tasktracker.dto.TaskCreateRequest;
import com.example.tasktracker.dto.TaskResponse;
import com.example.tasktracker.dto.TaskUpdateRequest;
import com.example.tasktracker.entity.Task;
import com.example.tasktracker.entity.TaskStatus;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.repository.TaskRepository;
import com.example.tasktracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Сервис для работы с задачами.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    /**
     * Spring сам передаст зависимости через конструктор.
     */
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    /**
     * Создает задачу для пользователя.
     *
     * @param request данные для создания задачи
     * @return созданная задача
     */
    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {

        // Проверяем, что пользователь существует
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь с id " + request.userId() + " не найден"
                ));

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(TaskStatus.TO_DO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    /**
     * Возвращает задачу по идентификатору.
     *
     * @param id идентификатор задачи
     * @return данные задачи
     */
    @Transactional(readOnly = true)
    public TaskResponse getTask(Long id) {
        Task task = findTaskById(id);

        return TaskResponse.from(task);
    }

    /**
     * Возвращает все задачи пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список задач пользователя
     */
    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByUserId(Long userId) {

        // Проверяем, что пользователь существует
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException(
                    "Пользователь с id " + userId + " не найден"
            );
        }

        return taskRepository.findAllByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    /**
     * Обновляет задачу.
     *
     * @param id      идентификатор задачи
     * @param request новые данные задачи
     * @return обновленная задача
     */
    @Transactional
    public TaskResponse updateTask(Long id, TaskUpdateRequest request) {

        Task task = findTaskById(id);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatus(request.status());

        return TaskResponse.from(task);
    }

    /**
     * Удаляет задачу.
     *
     * @param id идентификатор задачи
     */
    @Transactional
    public void deleteTask(Long id) {
        Task task = findTaskById(id);

        taskRepository.delete(task);
    }

    /**
     * Вспомогательный метод для поиска задачи.
     *
     * Если задача не найдена, бросаем NotFoundException.
     *
     * @param id идентификатор задачи
     * @return найденная задача
     */
    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Задача с id " + id + " не найдена"
                ));
    }
}