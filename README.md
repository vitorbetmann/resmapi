# Resmapi — Restaurant Management API

- [🇨🇦 English Description](#english-description)

- [🇧🇷 Descrição em Português](#descrição-em-português)

---

## 🇨🇦English Description

Backend developed for the **Tech Challenge — Phase 01** of the FIAP postgraduate program. The challenge proposes a
shared management system for restaurants, where owners can run their operations and customers can browse, review, and
place orders online. This first phase delivers the foundation of the system: **user management** (restaurant owners and
customers), with registration, updates, password change, search by name, and login validation.

## Stack

- Java 21
- Spring Boot 4.0.5 (Web, Data JPA, Validation)
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Lombok
- Maven
- Docker / Docker Compose

## How to run

The application is fully dockerized. The only requirements are Docker and Docker Compose.

### Start the containers

```bash
docker compose up --build
```

This launches two services:

- `db` — PostgreSQL with a persistent volume (`db-data`)
- `backend` — Spring Boot application exposed at `http://localhost:8080`

The `backend` waits for the database healthcheck before starting.

### API Documentation

`http://localhost:8080/swagger-ui.html`

## Endpoints

All endpoints are under the `/api/v1/users` prefix (URL versioning).

| Method | Path                          | Description                              |
|--------|-------------------------------|------------------------------------------|
| POST   | `/api/v1/users`               | Registers a new user (Owner or Customer) |
| PUT    | `/api/v1/users/{id}`          | Updates user data (excluding password)   |
| PATCH  | `/api/v1/users/{id}/password` | Password change (dedicated endpoint)     |
| DELETE | `/api/v1/users/{id}`          | Deletes the user                         |
| GET    | `/api/v1/users?name=...`      | Searches users by name (partial match)   |
| POST   | `/api/v1/users/login`         | Validates login and password             |

### Example — create user

```http
POST /api/v1/users
Content-Type: application/json

{
  "name": "Mauricio Borges",
  "email": "mauricio@example.com",
  "login": "mauricio",
  "password": "secret123",
  "address": "Av. Paulista, 1000",
  "type": "OWNER"
}
```

Valid types: `OWNER`, `CUSTOMER`.

### Error model (RFC 7807)

Errors follow the standard `ProblemDetail` format (RFC 7807):

```json
{
  "type": "about:blank",
  "title": "Conflict",
  "status": 409,
  "detail": "Email already in use"
}
```

| Exception                    | Status |
|------------------------------|--------|
| `UserNotFoundException`      | 404    |
| `InvalidUserTypeException`   | 400    |
| `EmailAlreadyInUseException` | 409    |
| `InvalidPasswordException`   | 401    |

## Data model

`User` is an abstract entity using JPA `SINGLE_TABLE` inheritance (discriminator column `type`). Concrete subclasses:

- `Owner` — restaurant owner (`type = "OWNER"`)
- `Customer` — customer (`type = "CUSTOMER"`)

Common fields: `id`, `name`, `email` (unique), `login`, `password`, `address`, `lastModifiedDate` (auto-populated via
`@LastModifiedDate`).

The single-table choice keeps lookups by name/email/login simple — they scan a single table covering every user type.

## Architecture

Layers are separated under `com.vitorbetmann.resmapi`:

```
controller/    REST endpoints (UserController)
services/      business logic (UserService)
repositories/  Spring Data JPA (UserRepository)
entities/      JPA entities (User, Owner, Customer)
dto/           immutable records with Bean Validation
exceptions/    domain exceptions + GlobalExceptionHandler
config/        OpenAPIConfig
```

`UserService` resolves the right subclass during registration through a `Map<String, Supplier<User>>` keyed by the
discriminator string — adding a new user type only requires registering a supplier and creating a subclass with
`@DiscriminatorValue`.

DTOs are `record`s with Jakarta Validation (`@NotBlank`, `@Email`). Auditing is enabled via `@EnableJpaAuditing` on the
main class.

## Postman collection

A Postman collection in JSON format is included in the repository in [Resmapi.postman_collection.json](Resmapi.postman_collection.json), covering:

Alternatively, to access the collection directly in Postman, click [here](https://vitorbetmann-5356326.postman.co/workspace/88c6f948-7512-4287-86ff-a0109b20c44c/collection/49832651-8d6986fc-d1d2-4980-ad1e-00915966295f?action=share&source=copy-link&creator=49832651)

- Valid and invalid registration (duplicate email, missing required fields)
- Successful and failing user data updates
- Successful and failing password changes
- Search users by name
- Login validation (success and failure)
- Deleting users by id

---

## 🇧🇷Descrição em Português

Backend desenvolvido para o **Tech Challenge — Fase 01** da pós-graduação FIAP. Trata-se de um sistema compartilhado de
gestão para restaurantes, em que os estabelecimentos podem administrar suas operações e os clientes podem consultar
informações, avaliar e fazer pedidos online. Esta primeira fase entrega a fundação do sistema: **gestão de usuários** (
donos de restaurante e clientes), com cadastro, atualização, troca de senha, busca por nome e validação de login.

## Stack

- Java 21
- Spring Boot 4.0.5 (Web, Data JPA, Validation)
- PostgreSQL
- springdoc-openapi (Swagger UI)
- Lombok
- Maven
- Docker / Docker Compose

## Como executar

A aplicação é totalmente dockerizada. Os requisitos são apenas Docker e Docker Compose.

### Iniciar os containers

```bash
docker compose up --build
```

Isso sobe dois serviços:

- `db` — PostgreSQL com volume persistente (`db-data`)
- `backend` — aplicação Spring Boot exposta em `http://localhost:8080`

O `backend` aguarda o healthcheck do banco antes de iniciar.

### Documentação da API

`http://localhost:8080/swagger-ui.html`

## Endpoints

Todos sob o prefixo `/api/v1/users` (versionamento via URL).

| Método | Caminho                       | Descrição                                    |
|--------|-------------------------------|----------------------------------------------|
| POST   | `/api/v1/users`               | Cadastra um novo usuário (Owner ou Customer) |
| PUT    | `/api/v1/users/{id}`          | Atualiza dados do usuário (exceto senha)     |
| PATCH  | `/api/v1/users/{id}/password` | Troca de senha (endpoint dedicado)           |
| DELETE | `/api/v1/users/{id}`          | Exclui o usuário                             |
| GET    | `/api/v1/users?name=...`      | Busca usuários pelo nome (match parcial)     |
| POST   | `/api/v1/users/login`         | Valida login e senha                         |

### Exemplo — criar usuário

```http
POST /api/v1/users
Content-Type: application/json

{
  "name": "Mauricio Borges",
  "email": "mauricio@example.com",
  "login": "mauricio",
  "password": "secret123",
  "address": "Av. Paulista, 1000",
  "type": "OWNER"
}
```

Tipos válidos: `OWNER`, `CUSTOMER`.

### Modelo de erro (RFC 7807)

Erros são padronizados via `ProblemDetail` (RFC 7807):

```json
{
  "type": "about:blank",
  "title": "Conflict",
  "status": 409,
  "detail": "Email already in use"
}
```

| Exception                    | Status |
|------------------------------|--------|
| `UserNotFoundException`      | 404    |
| `InvalidUserTypeException`   | 400    |
| `EmailAlreadyInUseException` | 409    |
| `InvalidPasswordException`   | 401    |

## Modelo de dados

`User` é uma entidade abstrata com herança JPA `SINGLE_TABLE` (coluna discriminadora `type`). Subclasses concretas:

- `Owner` — dono de restaurante (`type = "OWNER"`)
- `Customer` — cliente (`type = "CUSTOMER"`)

Campos comuns: `id`, `name`, `email` (único), `login`, `password`, `address`, `lastModifiedDate` (preenchido
automaticamente via `@LastModifiedDate`).

A escolha por single-table simplifica as consultas de busca por nome/email/login, que percorrem todos os tipos de
usuário em uma única tabela.

## Arquitetura

Camadas separadas em `com.vitorbetmann.resmapi`:

```
controller/    REST endpoints (UserController)
services/      regras de negócio (UserService)
repositories/  Spring Data JPA (UserRepository)
entities/      entidades JPA (User, Owner, Customer)
dto/           records imutáveis com Bean Validation
exceptions/    exceções de domínio + GlobalExceptionHandler
config/        OpenAPIConfig
```

`UserService` resolve a subclasse correta no cadastro através de um `Map<String, Supplier<User>>` indexado pelo
discriminador — para adicionar um novo tipo de usuário basta registrar um supplier e criar a subclasse com
`@DiscriminatorValue`.

DTOs são `record`s com validação via Jakarta (`@NotBlank`, `@Email`). Auditoria habilitada com `@EnableJpaAuditing` na
classe principal.

## Coleção Postman

A coleção em formato JSON está incluída no repositório em [Resmapi.postman_collection.json](Resmapi.postman_collection.json) cobrindo:

Alternativamente, para acessar a coleção diretamente em Postman, clique [aqui](https://vitorbetmann-5356326.postman.co/workspace/88c6f948-7512-4287-86ff-a0109b20c44c/collection/49832651-8d6986fc-d1d2-4980-ad1e-00915966295f?action=share&source=copy-link&creator=49832651)

- Cadastro válido e inválido (e-mail duplicado, campos faltando)
- Atualização de dados com sucesso e erro
- Troca de senha com sucesso e erro
- Busca de usuários pelo nome
- Validação de login (sucesso e falha)
- Deletar um usuário pelo id
