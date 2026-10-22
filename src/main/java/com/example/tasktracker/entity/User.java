package com.example.tasktracker.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность пользователя.
 *
 * Один пользователь может иметь много задач.
 * Связь One-to-Many настроена через поле tasks.
 */
@Entity
@Table(name = "users") // "user" может конфликтовать с зарезервированным словом в PostgreSQL
public class User {

    /**
     * Уникальный идентификатор пользователя.
     * Генерируется базой данных автоматически.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Email пользователя.
     * Используем как уникальный идентификатор для входа или поиска.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Список задач пользователя.
     *
     * Один пользователь может иметь много задач.
     * Задача владеет связью, поэтому внешняя таблица задач содержит поле user.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    /**
     * Пустой конструктор нужен для JPA/Hibernate.
     */
    public User() {
    }

    /**
     * Удобный конструктор для создания пользователя без задач.
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * Добавляет задачу пользователю и синхронизирует связь с обеих сторон.
     */
    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }

    /**
     * Удаляет задачу у пользователя и очищает связь.
     */
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }
}