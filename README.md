# Authentica 🔐

> **Authentica** é uma API robusta desenvolvida para ajudar a comunidade de QA a dominar testes de API, segurança e automação em ecossistemas Spring Boot.

[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.11-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4479A1?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

---

## 🚀 Sobre o Projeto

O projeto **Authentica** nasceu com a finalidade de servir como um laboratório prático para profissionais de Quality Assurance. Ele simula uma aplicação corporativa segura, permitindo que QAs pratiquem:

- **Autenticação JWT**: Fluxos de login, geração e expiração de tokens.
- **Autorização RBAC**: Testes de permissões e perfis de acesso (Admin vs User).
- **Fluxos de Recuperação**: Processos de "Esqueci minha senha" e redefinição via e-mail.
- **Gestão Administrativa**: Endpoints de CRUD para usuários, papéis (roles) e permissões.

## ✨ Funcionalidades Chave

- 🔑 **Autenticação Segura**: Login e Cadastro com criptografia de senhas e JWT.
- 🛡️ **Controle de Acesso (RBAC)**: Proteção de endpoints baseada em Roles (`ROLE_ADMIN`, `ROLE_USER`) e permissões específicas.
- 📧 **Recuperação de Senha**: Fluxo completo de token para recuperação de conta.
- 👥 **Painel Admin**: Endpoints dedicados para gerenciar o ecossistema de usuários do sistema.
- 📝 **Swagger Integrado**: Documentação viva que permite testar os endpoints diretamente pelo navegador.
- 🐳 **Docker-Ready**: Fácil de subir e escalar usando containers.

## 🛠️ Stack Tecnológica

| Camada | Tecnologia |
| :--- | :--- |
| **Linguagem** | Java 17 |
| **Framework Base** | Spring Boot 3.5.11 |
| **Segurança** | Spring Security 6 + JWT (jjwt) |
| **Persistência** | Spring Data JPA + PostgreSQL |
| **Migrations** | Flyway |
| **Documentação** | SpringDoc OpenAPI (Swagger) |
| **Produtividade** | Lombok |

## ⚙️ Configuração e Execução

### Pré-requisitos
- Java 17+
- Docker & Docker Compose (Recomendado)
- Maven (Opcional, se usar o `./mvnw` incluso)

### 🐋 Usando Docker (Mais rápido)
Para subir o banco de dados e a aplicação prontos para uso:
```bash
docker-compose up -d
```

### ☕ Execução Local (Maven)
1. Certifique-se de ter um PostgreSQL rodando na porta `5433` (ou ajuste as variáveis no `application.yml`).
2. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

## 🧪 Guia de Testes para QA

### 📖 Documentação Interativa (Swagger)
Explore todos os endpoints de forma visual:
👉 [Acesse o Swagger UI aqui](https://authenticaspringboot-production.up.railway.app/swagger-ui/index.html)

#### 🔑 Como Testar no Swagger (Passo-a-passo):
1. No Swagger, localize o endpoint `POST /auth/authenticate`.
2. Use as credenciais acima para fazer o login.
3. Copie o valor do `token` retornado no corpo da resposta.
4. Clique no botão verde **Authorize** no topo da página.
5. Cole o token e clique em **Authorize**.
6. Agora você pode testar todos os endpoints protegidos (como os de `/admin/**`).

### 📮 Postman Collection
Fornece instruções para importar a `Authentica_Postman_Collection.json`.

### 🔑 Credenciais de Acesso (Default Seed)
Utilize os dados abaixo para o primeiro acesso administrativo:
- **E-mail**: `admin@admin.com`
- **Senha**: `admin123`

### 💡 Sugestões de Cenários de Teste
1. **Segurança**: Tente acessar os endpoints `/admin/**` sem um token ou com um token de usuário sem perfil `ADMIN`.
2. **Validação**: Teste o endpoint de registro com e-mails inválidos ou senhas que não atendam aos requisitos.
3. **Exploração**: Verifique como a aplicação se comporta ao receber campos nulos ou tipos de dados inesperados nos DTOs.

## 🤝 Contribuindo

Sua iniciativa ajuda a fortalecer a comunidade de QA!
1. Faça um **Fork** do projeto.
2. Crie uma **Branch** para sua feature (`git checkout -b feature/SuaIniciativa`).
3. Dê um **Commit** nas mudanças (`git commit -m 'Add: Alguma melhoria'`).
4. Dê um **Push** para a Branch (`git push origin feature/SuaIniciativa`).
5. Abra um **Pull Request**.

## 👤 Autor

Desenvolvido por **Jonathas Matos**. Sinta-se à vontade para me procurar para trocarmos experiências!

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/jonathasmatos/)
[![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)](https://github.com/jonathasmatos)

---
⭐️ **Se este projeto te ajudou, deixe uma estrela no GitHub!**
