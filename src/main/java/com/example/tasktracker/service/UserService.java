package com.example.tasktracker.service;

import com.example.tasktracker.dto.UserCreateRequest;
import com.example.tasktracker.dto.UserResponse;
import com.example.tasktracker.entity.User;
import com.example.tasktracker.exception.NotFoundException;
import com.example.tasktracker.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с пользователями.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Spring сам передаст UserRepository через конструктор.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает нового пользователя.
     *
     * @param request данные для создания пользователя
     * @return данные созданного пользователя
     */
    @Transactional
    public UserResponse createUser(UserCreateRequest request) {

        // Проверяем, что пользователь с таким email еще не существует
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException(
                    "Пользователь с email " + request.email() + " уже существует"
            );
        }

        User user = new User(request.name(), request.email());

        User savedUser = userRepository.save(user);

        return UserResponse.from(savedUser);
    }

    /**
     * Возвращает пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return данные пользователя
     */
    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь с id " + id + " не найден"
                ));

        return UserResponse.from(user);
    }
}