package com.example.tasktracker.repository;

import com.example.tasktracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для работы с задачами.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    /**
     * Возвращает все задачи пользователя, отсортированные по дате создания.
     * Новые задачи будут в начале списка.
     *
     * @param userId идентификатор пользователя
     * @return список задач пользователя
     */
    List<Task> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}