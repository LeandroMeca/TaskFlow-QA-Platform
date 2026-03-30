# TaskManager - QA Automation Portfolio

Aplicação Fullstack (Spring Boot + Angular) para gerenciamento de tarefas, desenvolvida como um **Portfólio Avançado de Garantia de Qualidade (QA)**. 

O foco deste projeto não é apenas a construção do software, mas sim a implementação de uma **Pirâmide de Testes Completa** e uma esteira de **Integração Contínua (CI/CD)** robusta.

---

## 🚀 Tecnologias e Ferramentas

**Desenvolvimento:**
- **Backend:** Java 17, Spring Boot, Spring Data JPA, H2 Database em memória
- **Frontend:** Angular, TypeScript, Node.js

**Qualidade e Automação (QA):**
- **Testes Unitários (Backend):** JUnit 5, Mockito
- **Cobertura de Código:** JaCoCo
- **Testes Unitários (Frontend):** Vitest
- **Testes de API:** Postman, Newman
- **Testes E2E (Interface):** Selenium WebDriver (Java)
- **CI/CD:** GitHub Actions (Execução Headless)
- **Gestão Ágil:** Jira Software (Scrum/Kanban)

---

## 🎯 Estratégia de Testes

Este projeto cobre todas as camadas vitais de validação de software:

1. **Testes Unitários (Backend & Frontend):** Garantem que as regras de negócio isoladas (Services, Controllers, Components) funcionem perfeitamente. Cobertura de backend superior a 90% atestada pelo JaCoCo.
2. **Testes de API (Integração):** Validação de contratos, payloads e códigos de status HTTP (200, 201, 404) diretamente nas rotas REST utilizando Postman e automatizados via Newman.
3. **Testes E2E (Ponta a Ponta):** Scripts automatizados com Selenium que simulam o comportamento de um usuário real no navegador (CRUD completo, validação de popups nativos e sincronização de renderização).

---

## ⚙️ CI/CD Pipeline (GitHub Actions)

O projeto possui integração contínua configurada no diretório `.github/workflows/qa-pipeline.yml`. A cada novo *push* na branch `main`, o servidor na nuvem executa automaticamente:

1. Setup do ambiente (Ubuntu, Java 17, Node 20).
2. Inicialização do Backend (Spring Boot) e Frontend (Angular) em *background*.
3. Execução automatizada da Collection do Postman via **Newman**.
4. Execução da bateria de testes E2E do **Selenium em modo Headless**.
5. Relatório de aprovação da esteira.

---

## 📂 Estrutura do Projeto

~~~text
/
├── backend/                  # API RESTful (Spring Boot)
│   ├── src/main/java/        # Código fonte (Controllers, Services, Repositories)
│   └── src/test/java/        # Testes Unitários e E2E (Selenium)
├── frontend/                 # Interface Web (Angular)
│   ├── src/app/              # Componentes e Serviços de tela
│   └── src/app/*.spec.ts     # Testes Unitários do Frontend (Vitest)
├── postman/                  # Collections de Teste de API exportadas
└── .github/workflows/        # Arquivos de configuração da esteira CI/CD
~~~

---

## 💻 Como Executar Localmente

### 1. Rodar o Backend
~~~bash
cd backend
./mvnw spring-boot:run
~~~
*A API estará disponível em `http://localhost:8080`. O painel do banco de dados H2 pode ser acessado em `/h2-console` (JDBC URL: `jdbc:h2:mem:taskdb`).*

### 2. Rodar o Frontend
Em um novo terminal:
~~~bash
cd frontend
npm install
npm start
~~~
*A interface web estará disponível em `http://localhost:4200`.*

---

## 🧪 Como Executar os Testes Localmente

**Testes Unitários e E2E (Backend + Selenium):**
~~~bash
cd backend
./mvnw clean test
~~~
*O relatório de cobertura do JaCoCo será gerado em `target/site/jacoco/index.html`.*

**Testes Unitários (Frontend):**
~~~bash
cd frontend
npm run test
~~~

**Testes de API (Newman):**
Com o backend rodando, execute:
~~~bash
npx newman run ./postman/task-api.json
~~~

---

## 👤 Sobre o Projeto

Este projeto foi documentado, planejado e executado utilizando metodologias ágeis, simulando um ambiente real de desenvolvimento e validação contínua. É a prova prática do ciclo completo de Engenharia de Qualidade (SDLC).
