# TaskManager-Spring-QA

API REST para gerenciamento de tarefas desenvolvida com **Spring Boot** com foco em **testes automatizados e qualidade de software**.

O projeto demonstra:

- Arquitetura em camadas
- Testes unitários
- Testes de integração
- Pipeline CI com GitHub Actions

---

# Tecnologias

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito
- MockMvc
- JaCoCo
- GitHub Actions

---

# Arquitetura

O projeto segue uma **arquitetura em camadas**:

```
Controller
Service
Repository
Entity
```

Cada camada possui uma responsabilidade específica:

Controller → recebe requisições HTTP  
Service → lógica de negócio  
Repository → acesso ao banco de dados  
Entity → representação das entidades

---

# Estrutura do Projeto

```
src
 ├── main
 │   ├── java
 │   │   └── com.task.manager
 │   │        ├── controller
 │   │        │     TaskController.java
 │   │        ├── service
 │   │        │     TaskService.java
 │   │        ├── repository
 │   │        │     TaskRepository.java
 │   │        ├── entity
 │   │        │     Task.java
 │   │        └── Application.java
 │   │
 │   └── resources
 │        application.properties
 │
 └── test
     └── java
          └── com.task.manager
               ├── controller
               │     TaskControllerTest.java
               ├── service
               │     TaskServiceTest.java
               └── integration
                     TaskIntegrationTest.java
```

---

# Endpoints da API

## Listar tarefas

```
GET /tasks
```

---

## Criar tarefa

```
POST /tasks
```

Payload:

```json
{
  "title": "Estudar Spring Boot",
  "description": "Praticar testes automatizados",
  "completed": false
}
```

---

## Atualizar tarefa

```
PUT /tasks/{id}
```

Payload:

```json
{
  "title": "Atualizar tarefa",
  "description": "Descrição atualizada",
  "completed": true
}
```

---

## Deletar tarefa

```
DELETE /tasks/{id}
```

---

# Executando o Projeto

Rodar aplicação:

```
mvnw.cmd spring-boot:run
```

Ou

```
./mvnw spring-boot:run
```

---

# Executar Testes

Executar todos os testes:

```
mvnw.cmd test
```

---

# Executar Teste Específico

Executar apenas testes de service:

```
mvnw.cmd -Dtest=TaskServiceTest test
```

Executar testes de integração:

```
mvnw.cmd -Dtest=TaskIntegrationTest test
```

---

# Banco de Dados

O projeto utiliza **H2 Database em memória**.

Console do banco:

```
http://localhost:8080/h2-console
```

Configuração:

```
JDBC URL: jdbc:h2:mem:taskdb
```

---

# Cobertura de Testes

O projeto utiliza **JaCoCo** para cobertura de testes.

Após executar os testes o relatório estará disponível em:

```
target/site/jacoco/index.html
```

---

# CI Pipeline

O projeto possui **integração contínua utilizando GitHub Actions**.

A cada push ou pull request o pipeline executa:

- build da aplicação
- execução dos testes automatizados

Workflow localizado em:

```
.github/workflows/ci.yml
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

# Build do Projeto

Gerar build:

```
mvnw.cmd clean package
```

Executar o jar:

```
java -jar target/*.jar
```

---

# Próximos Passos

- Docker
- Testes de performance
- Monitoramento

---

# Autor

Projeto desenvolvido para estudo de **Spring Boot, testes automatizados e qualidade de software**.
