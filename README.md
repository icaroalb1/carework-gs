# 🔋 Carework - Sistema de Gestão de Energia e Bem-estar Operacional

Sistema web completo para monitoramento e gestão do bem-estar de equipes em turnos, com foco em segurança operacional e energia. Desenvolvido com Spring Boot, integra Inteligência Artificial Generativa para fornecer insights personalizados e recomendações baseadas em dados.

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias](#tecnologias)
- [Requisitos](#requisitos)
- [Instalação](#instalação)
- [Configuração](#configuração)
- [Executando a Aplicação](#executando-a-aplicação)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [API REST](#api-rest)
- [Frontend Web](#frontend-web)
- [Features Implementadas](#features-implementadas)
- [Deploy](#deploy)
- [Documentação](#documentação)
- [Contribuindo](#contribuindo)

## 🎯 Sobre o Projeto

O **Carework** é uma solução completa para gestão de bem-estar e segurança operacional de equipes que trabalham em turnos. O sistema permite:

- ✅ Registro de check-ins de humor, estresse e qualidade do sono
- ✅ Geração de relatórios semanais com análise de tendências
- ✅ Mensagens personalizadas geradas por IA (OpenAI) baseadas nos indicadores
- ✅ Sistema de dicas e recomendações para a equipe
- ✅ Dashboard web para visualização de dados
- ✅ API REST completa para integração com outros sistemas

## 🛠 Tecnologias

### Backend
- **Java 21**
- **Spring Boot 3.4.0**
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Autenticação e autorização
- **Spring AI** - Integração com OpenAI (GPT-4o-mini)
- **Spring Integration** - Mensageria assíncrona
- **Hibernate** - ORM
- **H2 Database** - Desenvolvimento
- **PostgreSQL** - Produção

### Frontend
- **Thymeleaf** - Template engine
- **Bootstrap** - Framework CSS
- **JavaScript** - Interatividade

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização
- **Swagger/OpenAPI** - Documentação da API
- **Lombok** - Redução de boilerplate

## 📦 Requisitos

- Java 21 ou superior
- Maven 3.6+ (ou use o wrapper `./mvnw`)
- Docker (opcional, para deploy)
- Chave da OpenAI (opcional, para usar IA generativa)

## 🚀 Instalação

### 1. Clone o repositório

```bash
git clone <url-do-repositorio>
cd carework-gs
```

### 2. Configure a chave da OpenAI (opcional)

Crie o arquivo `src/main/resources/application-local.yml`:

```yaml
spring:
  ai:
    openai:
      api-key: sua-chave-aqui
      chat:
        options:
          model: gpt-4o-mini
          temperature: 0.7
```

⚠️ **Importante**: Este arquivo está no `.gitignore` e não será commitado.

Ou use variável de ambiente:

```bash
export OPENAI_API_KEY="sua-chave-aqui"
```

## ⚙️ Configuração

### Perfis Disponíveis

- **dev** (padrão): Usa H2 em memória
- **local**: Usa configuração local (inclui chave OpenAI)
- **prod**: Configurado para PostgreSQL e Azure

### Variáveis de Ambiente

```bash
# API Key para autenticação
CAREWORK_API_KEY=carework-secret

# OpenAI (opcional)
OPENAI_API_KEY=sk-proj-...

# Produção - PostgreSQL
AZURE_DB_URL=jdbc:postgresql://...
AZURE_DB_USER=carework
AZURE_DB_PASSWORD=senha
```

## ▶️ Executando a Aplicação

### Desenvolvimento (sem OpenAI)

```bash
./mvnw spring-boot:run
```

### Desenvolvimento (com OpenAI)

```bash
./run-local.sh
```

Ou:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

### Com Docker

```bash
docker build -t carework-api-web .
docker run -p 8080:8080 carework-api-web
```

### Acessos

- **Aplicação Web**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console (dev apenas)

## 📁 Estrutura do Projeto

```
carework-gs/
├── src/
│   ├── main/
│   │   ├── java/com/carework/
│   │   │   ├── config/          # Configurações Spring
│   │   │   ├── controller/      # Controllers REST e Web
│   │   │   │   ├── api/        # Endpoints REST
│   │   │   │   └── web/        # Controllers MVC
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   ├── exception/      # Tratamento de exceções
│   │   │   ├── model/          # Entidades JPA
│   │   │   ├── repository/     # Repositories Spring Data
│   │   │   ├── security/       # Configuração de segurança
│   │   │   ├── service/        # Lógica de negócio
│   │   │   ├── messaging/      # Mensageria assíncrona
│   │   │   └── ai/             # Modelo local de IA
│   │   └── resources/
│   │       ├── templates/      # Templates Thymeleaf
│   │       ├── static/         # Arquivos estáticos
│   │       ├── messages.properties      # i18n (pt)
│   │       ├── messages_en.properties   # i18n (en)
│   │       └── application.yml         # Configurações
│   └── test/                   # Testes
├── Dockerfile                  # Container Docker
├── pom.xml                     # Dependências Maven
└── README.md                   # Este arquivo
```

## 🔌 API REST

### Autenticação

Todas as requisições (exceto `/api/auth/login`) requerem o header:

```
X-API-KEY: carework-secret
```

### Endpoints Principais

#### Autenticação
- `POST /api/auth/login` - Login de usuário
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```

#### Check-ins
- `GET /api/checkins` - Lista paginada de check-ins
  - Query params: `page`, `size`, `sort`
- `GET /api/checkins/user/{userId}` - Check-ins de um usuário
- `POST /api/checkins` - Cria novo check-in
  ```json
  {
    "userId": "uuid",
    "mood": 4,
    "stress": 2,
    "sleepQuality": 4
  }
  ```

#### Relatórios
- `GET /api/reports/weekly/{userId}` - Relatório semanal com análise de IA

#### Dicas
- `GET /api/tips` - Lista todas as dicas (cacheado)
- `POST /api/tips` - Cria nova dica
- `DELETE /api/tips/{id}` - Remove dica

#### Usuários
- `GET /api/users/{id}` - Busca usuário por ID

#### Eventos
- `GET /api/events` - Lista paginada de eventos de check-in

### Códigos de Status HTTP

- `200 OK` - Sucesso
- `201 Created` - Recurso criado
- `204 No Content` - Sucesso sem conteúdo
- `400 Bad Request` - Erro de validação
- `401 Unauthorized` - Não autenticado
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro interno

## 🌐 Frontend Web

O sistema inclui uma interface web completa com as seguintes páginas:

- **/** - Redireciona para `/home`
- **/home** - Dashboard principal com último check-in e relatório
- **/login** - Página de login
- **/checkin** - Formulário para criar check-in
- **/tips** - Lista de dicas e criação
- **/report** - Visualização de relatório semanal
- **/profile** - Perfil do usuário

### Internacionalização

O sistema suporta dois idiomas:
- **Português (pt)** - Padrão
- **Inglês (en)** - Via parâmetro `?lang=en`

## ✨ Features Implementadas

### ✅ Requisitos Técnicos Obrigatórios

1. **Anotações Spring** - Configuração de beans e injeção de dependências
2. **Model/DTO** - Separação adequada com métodos de acesso corretos
3. **Spring Data JPA** - Persistência com Hibernate
4. **Bean Validation** - Validação de dados com `@Valid`, `@NotNull`, `@Email`, etc.
5. **Caching** - Cache de dicas com `@Cacheable` e `@CacheEvict`
6. **Internacionalização** - Suporte a português e inglês
7. **Paginação** - Endpoints paginados com `Pageable`
8. **Spring Security** - Autenticação via API Key e autorização
9. **Tratamento de Erros** - `@RestControllerAdvice` com tratamento global
10. **Mensageria Assíncrona** - Spring Integration com filas
11. **Spring AI** - Integração com OpenAI para IA generativa
12. **Deploy em Nuvem** - Dockerfile e configuração para Azure
13. **REST API** - Verbos HTTP e códigos de status adequados

### 🎯 Features Adicionais

- **Swagger/OpenAPI** - Documentação automática da API
- **Thymeleaf Frontend** - Interface web completa
- **Health Checks** - Spring Actuator
- **Logging Estruturado** - Logs configurados
- **Arquitetura em Camadas** - Separação clara de responsabilidades

## 🚢 Deploy

### Docker

```bash
# Build
docker build -t carework-api-web .

# Run
docker run -p 8080:8080 \
  -e AZURE_DB_URL=jdbc:postgresql://... \
  -e AZURE_DB_USER=carework \
  -e AZURE_DB_PASSWORD=senha \
  -e OPENAI_API_KEY=sk-proj-... \
  carework-api-web
```

### Azure App Service

1. Configure as variáveis de ambiente no Azure Portal
2. Faça deploy do Dockerfile ou use Azure Container Registry
3. Configure o PostgreSQL no Azure Database

### Variáveis de Ambiente para Produção

```bash
SPRING_PROFILES_ACTIVE=prod
AZURE_DB_URL=jdbc:postgresql://...
AZURE_DB_USER=carework
AZURE_DB_PASSWORD=senha
OPENAI_API_KEY=sk-proj-...
CAREWORK_API_KEY=chave-secreta-producao
```

## 📚 Documentação

### Swagger UI

Acesse http://localhost:8080/swagger-ui.html para documentação interativa da API.

### Documentação Adicional

- `OPENAI_SETUP.md` - Como configurar a OpenAI
- `RESUMO_USO_OPENAI.md` - Detalhes sobre o uso da IA
- `CHECKLIST_REQUISITOS.md` - Checklist completo de requisitos

## 🧪 Testes

```bash
# Executar todos os testes
./mvnw test

# Executar com cobertura
./mvnw test jacoco:report
```

## 🔒 Segurança

- Autenticação via API Key
- Senhas criptografadas com BCrypt
- Validação de entrada com Bean Validation
- Tratamento seguro de exceções
- CORS configurado adequadamente

## 📝 Licença

Este projeto foi desenvolvido como trabalho acadêmico.

## 👥 Autores

Desenvolvido para o curso de Java Advanced.

## 🙏 Agradecimentos

- Spring Framework
- OpenAI
- Comunidade Spring Boot

---

**Desenvolvido com ❤️ usando Spring Boot**

