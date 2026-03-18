<p align="left">
  Este repositório contém o projeto TaskManager-Spring-QA e exemplifica um ecossistema de microsserviços para avaliação de crédito e emissão de cartões, seguindo o padrão solicitado: uso de Spring Cloud, comunicação síncrona (OpenFeign) e assíncrona (RabbitMQ).
</p>

---

## 📌 Visão geral

Este README seguirá o padrão de arquitetura de microsserviços (Eureka, Gateway, microsserviços de Clientes/Cartões/Avaliador) e inclui instruções para executar localmente componentes essenciais como RabbitMQ e Keycloak.

> Observação: o código presente neste repositório implementa um serviço de exemplo chamado `manager` (classe principal em `src/main/java/com/task/manager/Application.java`) com um controlador de tarefas (`TaskController`) usado para demonstração. Em uma arquitetura completa, cada bloco descrito abaixo normalmente existe em repositórios/módulos separados.

---

## 📊 Arquitetura e Fluxo

O diagrama (arquivo editável em `docs/architecture.mmd`) representa a interação entre os serviços. O microsserviço `msavaliadorcredito` atua como orquestrador, agregando dados e emitindo mensagens para a fila `emissao-cartoes` quando necessário.

Se quiser editar o diagrama, abra `docs/architecture.mmd` (Mermaid) e re-gere `docs/architecture.svg`.

---

## 🚀 Serviços do Ecossistema (descrição)

Nota: este repositório contém um serviço de exemplo (Tasks). Abaixo descrevo o conjunto de microsserviços esperado no padrão.

### 🔍 Eureka Server (`eurekaserver`)

Responsável pelo Service Discovery. Porta típica: `8761`.

### ☁️ Cloud Gateway (`mscloudgateway`)

API Gateway que centraliza o acesso externo e roteia requisições. Em exemplos, costuma usar a porta `8080`.

Rotas (exemplo):

- `/clientes/**` → `msclientes`
- `/cartoes/**` → `mscartoes`
- `/avaliacoes-credito/**` → `msavaliadorcredito`

### 👤 MS Clientes (`msclientes`)

Gerencia dados cadastrais dos clientes (Nome, CPF, Idade). Tecnologias típicas: Spring Data JPA + H2 (dev).

### 💳 MS Cartões (`mscartoes`)

Gerencia tipos de cartões e cartões emitidos. Também consome mensagens da fila RabbitMQ (`emissao-cartoes`).

### 📊 MS Avaliador de Crédito (`msavaliadorcredito`)

Serviço orquestrador que integra `msclientes` e `mscartoes`. Usa OpenFeign para chamadas síncronas e RabbitMQ para mensagens assíncronas (emissão de cartão).

---

## 🔧 Conteúdo deste repositório

- Código fonte Java Spring Boot em `src/main/java/com/task/manager`
- Exemplo de controlador: `TaskController` expõe endpoints REST para gerenciamento de tarefas (lista e criação) — útil para testar infraestrutura local (RabbitMQ/Keycloak/Gateway).

### Endpoints (implementados neste repositório)

- GET /tasks → lista todas as tasks
- POST /tasks → cria uma nova task (body: JSON da entidade Task)

Exemplo de payload para POST /tasks:

```json
{
  "title": "Exemplo",
  "description": "Tarefa de teste"
}
```

Observação: as rotas observadas no código-fonte deste repositório estão em `src/main/java/com/task/manager/controller/TaskController.java`.

---

## 🛠️ Stack Tecnológico

<p align="center">
  <img src="https://img.shields.io/badge/Java-11-orange" />
  <img src="https://img.shields.io/badge/Spring%20Boot-2.6.x-green" />
  <img src="https://img.shields.io/badge/Spring%20Cloud-2021.0.1-blue" />
  <img src="https://img.shields.io/badge/RabbitMQ-Mensageria-orange" />
  <img src="https://img.shields.io/badge/Docker-Container-blue" />
  <img src="https://img.shields.io/badge/Keycloak-OpenID%20Connect-8A2BE2" />
</p>

---

## ⚙️ Configuração de Mensageria

Fila usada no padrão do projeto (conforme `MQConfig.java` esperado):

```text
emissao-cartoes
```

No ecossistema, `mscartoes` costuma ser o consumidor dessa fila para efetivar a emissão do cartão.

---

## 🔐 Autenticação e Autorização (Keycloak)

Este README inclui uma seção com instruções para rodar o Keycloak em modo de desenvolvimento e integrá-lo localmente.

### Rodar o Keycloak (modo dev)

Executar o container do Keycloak (porta 8180 neste exemplo, para evitar conflito com aplicações locais):

```powershell
docker run -p 8180:8080 \
  -e KEYCLOAK_ADMIN=admin \
  -e KEYCLOAK_ADMIN_PASSWORD=admin \
  quay.io/keycloak/keycloak:latest start-dev
```

- Console de administração: http://localhost:8180/
- Usuário/Senha (dev): `admin` / `admin` (conforme variáveis acima)

### Configuração mínima (via console Keycloak)

1. Criar um Realm (ex.: `ms-realm`).
2. Criar um Client (ex.: `ms-client`) com Access Type `public` ou `confidential` (dev: `public`).
3. Criar usuários de teste (ex.: `usuario` / senha `senha`).

Para ambientes de produção, não use `start-dev` e configure HTTPS, certificados e políticas de segurança.

### Obter token (exemplo dev - grant_type=password)

```bash
curl -s -X POST "http://localhost:8180/realms/ms-realm/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=usuario" \
  -d "password=senha" \
  -d "grant_type=password" \
  -d "client_id=ms-client"
```

Resposta: JSON com `access_token`. Use no header das requisições:

```
Authorization: Bearer <access_token>
```

### Integração com Spring Boot

Configure `spring.security.oauth2.resourceserver.jwt.issuer-uri` (ou `jwk-set-uri`) apontando para o issuer do Keycloak (ex.: `http://localhost:8180/realms/ms-realm`). Proteja endpoints conforme roles.

Se quiser, posso gerar exemplos de `application.yml` para o `mscloudgateway` e para o `msavaliadorcredito` com as propriedades `spring.security.oauth2` já prontas.

---

## 🧪 Testes com Insomnia

Recomendo usar o Insomnia para testar endpoints e automatizar a obtenção do token JWT do Keycloak.

Exemplo rápido de Environment no Insomnia:

```json
{
  "base_url": "http://localhost:8080",
  "keycloak_url": "http://localhost:8180",
  "realm": "ms-realm",
  "client_id": "ms-client",
  "username": "usuario",
  "password": "senha"
}
```

Fluxo:

1. Request POST para `{{ keycloak_url }}/realms/{{ realm }}/protocol/openid-connect/token` para obter `access_token`.
2. Salvar `access_token` no Environment do Insomnia.
3. Testar endpoints protegidos com header `Authorization: Bearer {{ access_token }}`.

Exemplos úteis (endpoints deste repositório):

- Criar task (POST): `POST {{ base_url }}/tasks` — body JSON
- Listar tasks (GET): `GET {{ base_url }}/tasks`

---

## ▶️ Como Executar o Projeto (local)

### 1️⃣ Subir o RabbitMQ (console de gerenciamento)

```powershell
docker run -it --rm --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  rabbitmq:3.9-management
```

- Console: http://localhost:15672 (guest/guest)

### 2️⃣ Subir o Keycloak (opcional, para autenticação)

Veja a seção Keycloak acima (porta 8180 no exemplo).

### 3️⃣ Executar a aplicação Spring Boot (neste repositório)

No Windows (PowerShell) use o wrapper:

```powershell
.\\mvnw.cmd clean spring-boot:run
```

No Linux/macOS:

```bash
./mvnw clean spring-boot:run
```

Ou buildar o jar e executar:

```powershell
.\\mvnw.cmd -DskipTests package
java -jar target/*.jar
```

Observação: não foi definida explicitamente a propriedade `server.port` em `src/main/resources/application.properties`, portanto a aplicação usa a porta padrão `8080`. Se o Gateway também usar `8080`, ajuste `server.port` ou execute via perfil diferente.

### 4️⃣ Testes

Executar os testes unitários:

```powershell
.\\mvnw.cmd test
```

---

## ✅ Observações Finais

- Arquitetura baseada em microsserviços desacoplados.
- Demonstração de Service Discovery, API Gateway e Mensageria (RabbitMQ).
- Ideal para estudar Spring Cloud, OpenFeign e integração com Keycloak.

---

## Próximos passos que posso ajudar a automatizar

- Gerar versão em inglês do README
- Adaptar README para apresentação em portfólio GitHub
- Adicionar exemplos cURL e coleção Postman/Insomnia exportável
- Gerar `application.yml` de exemplo para Gateway e para `msavaliadorcredito` com OIDC

Se quiser que eu aplique alguma dessas opções, diga qual delas e eu implemento.
# TaskManager-Spring-QA