# TaskManager-Spring-QA

API REST simples para gerenciamento de tarefas construída com Spring Boot com foco em **testes automatizados**.

O objetivo do projeto é demonstrar boas práticas de desenvolvimento backend e estratégias de testes utilizando JUnit, testes de integração e cobertura de código.

---

# Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit 5
- MockMvc
- Mockito
- JaCoCo

---

# Arquitetura

A aplicação utiliza uma arquitetura em camadas:

```
Controller
Service
Repository
Entity
```

---

# Estrutura do Projeto

```
src
 ├── main
 │   ├── java
 │   │   └── com.task.manager
 │   │        ├── controller
 │   │        │     └── TaskController.java
 │   │        ├── service
 │   │        │     └── TaskService.java
 │   │        ├── repository
 │   │        │     └── TaskRepository.java
 │   │        ├── entity
 │   │        │     └── Task.java
 │   │        └── Application.java
 │   └── resources
 │        └── application.properties
 │
 └── test
     └── java
          └── com.task.manager
               ├── controller
               │     └── TaskControllerTest.java
               ├── service
               │     └── TaskServiceTest.java
               └── integration
               │      └── TaskIntegrationTest.java
               └── e2e
                     └── TaskE2ETest.java
```

---

# Endpoints da API

### Listar tarefas

```
GET /tasks
```

---

### Criar tarefa

```
POST /tasks
```

Payload:

```json
{
  "title": "Estudar Spring",
  "description": "Praticar testes com JUnit",
  "completed": false
}
```

---

### Atualizar tarefa

```
PUT /tasks/{id}
```

Payload:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Atualizando tarefa",
  "completed": true
}
```

---

### Deletar tarefa

```
DELETE /tasks/{id}
```

---

# Executando o Projeto

Executar aplicação:

```
./mvnw spring-boot:run
```

Ou no Windows:

```
mvnw.cmd spring-boot:run
```

---

# Executar Testes

Executar todos os testes:

```
./mvnw test
```

Windows:

```
mvnw.cmd test
```

---

# Executar Teste Específico

Executar apenas testes de Service:

```
mvnw.cmd -Dtest=TaskServiceTest test
```

Executar testes de integração:

```
mvnw.cmd -Dtest=TaskIntegrationTest test
```

---

# Banco de Dados

O projeto utiliza **H2 Database em memória** para facilitar testes e execução local.

Console do H2:

```
http://localhost:8080/h2-console
```

Configuração:

```
JDBC URL: jdbc:h2:mem:taskdb
```

---

# Cobertura de Testes

O projeto utiliza **JaCoCo** para análise de cobertura de testes.

Após rodar os testes o relatório será gerado em:

```
target/site/jacoco/index.html
```

---

# Exemplo usando cURL

Criar tarefa:

```
curl -X POST http://localhost:8080/tasks \
-H "Content-Type: application/json" \
-d '{"title":"Estudar API","description":"Praticar Spring Boot","completed":false}'
```

Listar tarefas:

```
curl http://localhost:8080/tasks
```

---

# Executar Build

```
mvnw.cmd clean package
```

Executar o jar:

```
java -jar target/*.jar
```

---

# Próximos Passos

- Pipeline CI/CD
- Dockerização da aplicação

---

# Autor

Projeto criado para estudos de **Spring Boot, testes automatizados e QA**.
