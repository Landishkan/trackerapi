package com.example.tasktracker.entity;

/**
 * Статус задачи.
 */
public enum TaskStatus {

    // Задача создана, но еще не начата
    TO_DO,

    // Задача в процессе выполнения
    IN_PROGRESS,

    // Задача завершена
    DONE
}