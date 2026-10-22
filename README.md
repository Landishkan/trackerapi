
Task Tracker API

Небольшой пет-проект: REST API для трекера задач.

Проект я сделала уже довольно давно, но только сейчас решила привести его в порядок и выложить на GitHub.  
Использовала его, чтобы попрактиковаться с Java, Spring Boot, REST API и работой с базой данных.

## Описание

Это простой трекер задач, в котором можно:

- создать пользователя;
- создавать задачи пользователя;
- получать задачи пользователя;
- обновлять задачу;
- удалять задачу.

Сущности:

- `User` — пользователь;
- `Task` — задача пользователя.

Между ними связь `One-to-Many`: у одного пользователя может быть много задач.

## Технологии

- Java 17
- Spring Boot 3
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- JUnit 5
- Mockito

## Как запустить

### 1. Поднять PostgreSQL через Docker

В корне проекта выполнить:

```bash
docker compose up -d
```

PostgreSQL будет доступен на `localhost:5432`.

База данных:

```text
tasktracker
```

Пользователь:

```text
postgres
```

Пароль:

```text
postgres
```

### 2. Запустить приложение

```bash
mvn spring-boot:run
```

Приложение запустится на:

```text
http://localhost:8080
```

## Примеры запросов

Создать пользователя:

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ирина",
    "email": "irina@example.com"
  }'
```

Создать задачу:

```bash
curl -X POST http://localhost:8080/api/v1/tasks \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Сделать пет-проект",
    "description": "REST API для трекера задач",
    "userId": 1
  }'
```

Получить задачи пользователя:

```bash
curl http://localhost:8080/api/v1/tasks/user/1
```

## Тесты

Запуск тестов:

```bash
mvn test
```

## Примечание

Это учебный пет-проект. Он не предназначен для продакшена, но его можно использовать как пример базовой структуры Spring Boot REST API.
