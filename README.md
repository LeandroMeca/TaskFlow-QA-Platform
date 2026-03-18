\*\*\* Begin README

# TaskManager-Spring-QA

Uma API REST simples em Spring Boot para gerenciamento de tarefas (Task Manager).

Este repositório é um exemplo didático: implementa endpoints básicos para listar e criar tarefas, persiste em H2 (memória) e vem acompanhado de testes unitários. É ideal para estudar uma API Spring Boot pequena com persistência em memória.

---

## Visão geral

Pontos principais do projeto:

- Endpoints para gerenciar tarefas (`/tasks`) — listagem e criação.
- Persistência usando H2 em memória (configurada em `src/main/resources/application.properties`).
- Entidade `Task` com campos `id`, `title`, `description` e `completed`.
- Testes unitários em `src/test/java/com/task/manager`.

### Endpoints

- GET /tasks → lista todas as tasks
- POST /tasks → cria uma nova task (body: JSON da entidade Task)

Exemplo de payload para POST /tasks:

```json
{
  "title": "Exemplo",
  "description": "Tarefa de teste",
  "completed": false
}
```

---

## Tecnologias

- Java 17
- Spring Boot 3.x (parent 3.5.11)
- Spring Data JPA
- H2 (in-memory)
- Lombok
- Maven

---

## Como executar

No Windows (PowerShell):

```powershell
.\mvnw.cmd clean spring-boot:run
```

Gerar jar e executar:

```powershell
.\mvnw.cmd -DskipTests package
java -jar target/*.jar
```

Definir porta alternativa:

```powershell
.\mvnw.cmd spring-boot:run -Dspring-boot.run.arguments="--server.port=9090"
```

---

## H2 Console

Console H2 disponível em: `http://localhost:8080/h2-console`

JDBC URL: `jdbc:h2:mem:taskdb`

---

## Estrutura do projeto

```
src/
  main/
    java/com/task/manager/
      Application.java
      controller/TaskController.java
      entity/Task.java
      repository/TaskRepository.java
      service/TaskService.java
    resources/
      application.properties
  test/
    java/com/task/manager/
```

---

## Entidade Task

A entidade `Task` (em `src/main/java/com/task/manager/entity/Task.java`) possui:

- `id: Long` (PK)
- `title: String`
- `description: String`
- `completed: boolean`

---

## Exemplos (cURL)

Criar uma task:

```bash
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Comprar leite","description":"Lembrar de comprar leite","completed":false}'
```

Listar tasks:

```bash
curl http://localhost:8080/tasks
```

---

## Testes

Executar testes unitários:

```powershell
.\mvnw.cmd test
```

---

## Observações

- Projeto pequeno e auto-contido — não depende de serviços externos para executar (usa H2 em memória).
- Ideal para aprendizado e como base para evoluções (persistência externa, autenticação, etc.).

---

If you want any changes (README in English, Postman/Insomnia collection, or CI pipeline), tell me which and I can add them.

\*\*\* End README
