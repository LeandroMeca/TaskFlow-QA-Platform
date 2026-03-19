# TaskManager-Spring-QA — README focado em TESTES

Este repositório é uma aplicação de exemplo em Spring Boot cujo objetivo principal deste fork/versão é servir como base para desenvolvimento e experimentos de testes automatizados (unitários e de integração).

Resumo rápido
- Projeto: API REST simples para gerenciamento de tarefas (`/tasks`).
- Principal objetivo aqui: demonstrar padrões de teste em Spring Boot (Web layer unit tests com MockMvc, testes de serviço, testes de integração com SpringBootTest + H2 in-memory).

Por que este README está focado em testes
- Para que outros desenvolvedores consigam executar, entender e estender a suíte de testes rapidamente.
- Documenta onde estão os testes, que tipos existem e como executá-los isoladamente.


---

## Onde ficam os testes

- Testes unitários e de controller:
  - `src/test/java/com/task/manager/controller/TaskControllerTest.java` (ex.: usa @WebMvcTest + MockMvc + Mockito)
  - `src/test/java/com/task/manager/service/TaskServiceTest.java` (service-level unit tests)
- Testes de integração:
  - `src/test/java/com/task/manager/integration/TaskIntegrationTest.java` (usa @SpringBootTest + @AutoConfigureMockMvc e H2 in-memory)
- Outros testes utilitários:
  - `src/test/java/com/task/manager/ApplicationTests.java`, `TestExample.java`

---

## O que cada tipo de teste cobre

- Unit tests (service/controller): isolam camadas usando mocks. Exemplo: `TaskControllerTest` valida o comportamento do controller com `MockMvc` e um `TaskService` mockado.
- Integration tests: sobem o contexto Spring (sem dependências externas), usam H2 em memória para persistência e `MockMvc` para simular requisições HTTP reais. Exemplo: `TaskIntegrationTest` cria/atualiza/deleta tarefas através dos endpoints.

---

## Comandos úteis (PowerShell)

Executar toda a suíte de testes (unit + integração):

```powershell
.\mvnw.cmd test
```

Executar apenas testes unitários por classe (ex.: `TaskServiceTest`):

```powershell
.\mvnw.cmd -Dtest=TaskServiceTest test
```

Executar apenas testes de integração por classe (ex.: `TaskIntegrationTest`):

```powershell
.\mvnw.cmd -Dtest=TaskIntegrationTest test
```

Executar um único método de teste (ex.: `shouldReturnTask`):

```powershell
.\mvnw.cmd -Dtest=TaskControllerTest#shouldReturnTask test
```

Observação: o Maven Surefire executor aceita padrões para `-Dtest`. Use isso quando quiser acelerar validações locais.

---

## Como os testes funcionam (detalhes rápidos)

- `@WebMvcTest` (controller unit tests): inicializa apenas a camada web, usando `MockMvc` para requisições e mocks para dependências (@MockitoBean ou @MockBean).
- `@SpringBootTest` + `@AutoConfigureMockMvc` (integration tests): inicializa o contexto completo da aplicação. O projeto usa H2 em memória (`jdbc:h2:mem:taskdb`) para deixar os testes determinísticos e sem dependências externas.
- `ObjectMapper` é injetado nos testes de integração para serializar/deserializar JSON.

---

## Testes existentes (resumo das classes)

- `TaskControllerTest` — verifica que o endpoint GET `/tasks` retorna os dados esperados usando `MockMvc` e um `TaskService` simulado.
- `TaskServiceTest` — (se presente) valida a lógica da camada de serviço (save, update, delete) isolada do repositório.
- `TaskIntegrationTest` — executa fluxos completos: cria uma task, atualiza e deleta verificando códigos HTTP esperados (200/204) — usa H2 e o repositório real.

---

## Executando testes em CI

Recomendo adicionar um workflow GitHub Actions simples que execute `mvn -B test` em cada push/PR. Se quiser, eu posso adicionar um arquivo `.github/workflows/maven.yml` com isso.

Exemplo rápido de job (resumido):

```yaml
name: Java CI
on: [push, pull_request]
jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 17
        uses: actions/setup-java@v4
        with:
          java-version: '17'
          distribution: 'temurin'
      - name: Build and test
        run: ./mvnw -B test
```

---

## Cobertura de testes (opcional)

Posso adicionar `jacoco-maven-plugin` ao `pom.xml` para gerar relatórios de cobertura. Exemplo de uso local:

```powershell
.\mvnw.cmd test jacoco:report
```

---

## Dicas rápidas para contribuir com testes

- Prefira testes pequenos e determinísticos.
- Use `@WebMvcTest` para validar apenas a camada web e `@SpringBootTest` para fluxos que envolvem JPA/H2.
- Use nomes de métodos de teste descritivos (ex.: shouldCreateTaskWhenValidPayload).
- Execute testes localmente antes de abrir PR: `.\mvnw.cmd -Dtest=YourTest test`.

---

## Próximos passos que posso implementar

- Adicionar workflow GitHub Actions para rodar os testes em pushes/PRs.
- Adicionar Jacoco e gerar relatório de cobertura.
- Gerar uma coleção Postman/Insomnia com os requests usados nos testes de integração.

Diga qual desses você quer que eu implemente agora.
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

- PUT /tasks/{id} → atualiza uma task existente (body: JSON da entidade Task)
- DELETE /tasks/{id} → deleta a task com o id informado

Exemplo de payload para POST /tasks:

```json
{
  "title": "Exemplo",
  "description": "Tarefa de teste",
  "completed": false
}
```

Exemplo de payload para PUT /tasks/{id} (atualizar):

```json
{
  "title": "Exemplo Atualizado",
  "description": "Descrição atualizada",
  "completed": true
}
```

Exemplos cURL adicionais:

Atualizar task (PUT):

```bash
curl -X PUT http://localhost:8080/tasks/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Exemplo Atualizado","description":"Descrição atualizada","completed":true}'
```

Deletar task (DELETE):

```bash
curl -X DELETE http://localhost:8080/tasks/1
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

## Fluxo
<img width="2520" height="713" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/75bd4fde-4040-48b4-ac13-6c0bdc9990e1" />

## Resultado
<img width="1301" height="272" alt="Captura de tela 2026-03-18 221914" src="https://github.com/user-attachments/assets/7455eaef-b641-40b1-82b5-e41c884def9b" />

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

## Testes adicionais e cobertura

O projeto contém tanto testes unitários quanto testes de integração:

- Testes unitários (ex.: `TaskServiceTest`, `TaskControllerTest`) — localizados em `src/test/java/com/task/manager`.
- Testes de integração (ex.: `TaskIntegrationTest`) — fazem chamadas HTTP simuladas contra o contexto Spring Boot e verificam os fluxos de criação/atualização/deleção.

Para executar todos os testes (unitários + integração):

```powershell
.\mvnw.cmd test
```

Se quiser, posso adicionar um relatório de cobertura simples (Jacoco) ou um workflow do GitHub Actions que execute os testes em cada push/PR.

---

## Observações

- Projeto pequeno e auto-contido — não depende de serviços externos para executar (usa H2 em memória).
- Ideal para aprendizado e como base para evoluções (persistência externa, autenticação, etc.).

---

If you want any changes (README in English, Postman/Insomnia collection, or CI pipeline), tell me which and I can add them.

\*\*\* End README
