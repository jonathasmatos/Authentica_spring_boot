# Analise tecnica para migracao da API Spring Boot para Railway com Docker

## 1. BUILD E EMPACOTAMENTO

- O projeto gera JAR executavel fat jar: sim.
- Evidencia: plugin spring-boot-maven-plugin com fase repackage ativa no build.
- Comando exato de build recomendado:
  - .\\mvnw.cmd clean package -DskipTests
- Resultado validado:
  - BUILD SUCCESS
  - Artefato: target/authentica-0.0.1-SNAPSHOT.jar
  - Artefato original: target/authentica-0.0.1-SNAPSHOT.jar.original
- Dependencia externa fora do JAR: sim.
  - Banco PostgreSQL
  - Servico SMTP (Mailtrap hoje)
- Uso do Spring Boot Maven Plugin: correto para fat jar.

## 2. DOCKERIZACAO

Dockerfile ideal para este projeto:

~~~dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN chmod +x mvnw || true
RUN ./mvnw -q -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/authentica-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
~~~

- O container sobe sem dependencias externas: nao.
  - Precisa de banco PostgreSQL acessivel.
  - Fluxo de recuperacao de senha depende de SMTP.
- A aplicacao inicia dentro do container: sim, desde que variaveis de banco e JWT estejam corretas.
- Ajuste para Linux necessario: sim.
  - Evitar localhost no datasource.
  - Usar variaveis de ambiente para porta, banco, jwt e mail.

## 3. CONFIGURACAO DE PORTA

- Preparada para PORT: nao.
- Hoje usa porta fixa:
  - server.port: 8080
- Ajuste necessario em application.yml:

~~~yaml
server:
  port: ${PORT:8080}
~~~

## 4. BANCO DE DADOS

- Usa PostgreSQL local localhost: sim.
  - jdbc:postgresql://localhost:5433/postgres
- Adaptacao para Railway:
  - trocar URL fixa por variavel de ambiente.
  - exemplo recomendado:

~~~yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
    driver-class-name: org.postgresql.Driver
~~~

- Controle de migration: sim, Flyway.
- Sobe com banco vazio: sim, se DB estiver acessivel e permissoes ok.
  - Flyway aplica V1 a V12.
  - Existem scripts de seed de usuario admin e papeis.

## 5. VARIAVEIS DE AMBIENTE

Variaveis minimas necessarias:

- PORT
- DB_URL
- DB_USER
- DB_PASSWORD
- JWT_SECRET

Variaveis recomendadas adicionais:

- SPRING_PROFILES_ACTIVE=prod
- JAVA_OPTS=-XX:+UseContainerSupport -XX:MaxRAMPercentage=75 -Dfile.encoding=UTF-8
- MAIL_HOST
- MAIL_PORT
- MAIL_USER
- MAIL_PASSWORD

Mapeamento recomendado no application.yml:

~~~yaml
api:
  security:
    token:
      secret: ${JWT_SECRET}

spring:
  mail:
    host: ${MAIL_HOST:smtp.mailtrap.io}
    port: ${MAIL_PORT:2525}
    username: ${MAIL_USER:user_mock}
    password: ${MAIL_PASSWORD:pass_mock}
~~~

Valores hardcoded identificados:

- Segredo JWT hardcoded em application.yml.
- URL de banco localhost hardcoded em application.yml.
- Senhas e hashes de usuarios seed em migrations.
- Credenciais mail mock hardcoded em application.yml.

## 6. AUTENTICACAO E ACESSO

- Existe usuario inicial para login: sim, via migrations.
- Estado final de seed aponta usuario principal:
  - email: admin@admin.com
  - senha definida por hash nas migracoes V11/V12
- Como garantir acesso apos deploy:
  - manter seed Flyway ativo
  - validar login no endpoint /auth/authenticate
  - opcionalmente criar migration de seed exclusiva para producao com credenciais controladas por variavel
- Necessidade de seed: sim, para primeiro acesso administrativo.

## 7. CORS E ACESSO EXTERNO

- A API permite requisicoes externas hoje: parcialmente.
  - CORS esta restrito a:
    - http://localhost:5173
    - http://127.0.0.1:5173
    - http://localhost:8080
- Para Railway e front externo: precisa ajustar.
- Recomendacao:
  - permitir origem via variavel CORS_ALLOWED_ORIGINS (lista separada por virgula) e aplicar no SecurityConfig.

## 8. LOGS E OBSERVABILIDADE

- Logs no console: sim.
- Startup medido localmente:
  - Started AuthenticaApplication in 7.776 seconds.
- Garantir debug em producao:
  - manter logs no stdout (Railway captura)
  - nao habilitar debug global em producao
  - controlar nivel por variavel (ex.: LOG_LEVEL_ROOT)
  - opcional: habilitar actuator health/info para monitoramento

## 9. SEGURANCA

- Swagger exposto: sim, publico.
- Endpoints admin protegidos: sim.
  - Classe AdminController com @PreAuthorize("hasRole('ADMIN')")
- Riscos identificados:
  - JWT_SECRET hardcoded e com fallback default no codigo de JwtService/JwtParser
  - Swagger publico em producao aumenta superficie de ataque
  - Credenciais de seed previsiveis em migrations
  - CORS limitado a localhost (quebra em producao e induz ajustes manuais de risco)

## 10. DEPENDENCIAS EXTERNAS

- Dependencias externas:
  - PostgreSQL
  - SMTP
- Tratamento em cloud:
  - injetar todas as credenciais por variavel de ambiente
  - validar conectividade na inicializacao
  - definir timeout e retry no SMTP se necessario

## 11. PERFORMANCE E INICIALIZACAO

- Tempo de startup observado: 7.776s.
- Possiveis gargalos no deploy:
  - conexao com banco indisponivel
  - Flyway em banco novo com latencia alta
  - primeiro build Docker sem cache Maven
  - falta de memoria se JAVA_OPTS nao estiver ajustado para container

## 12. ESTRATEGIA DE DADOS INICIAIS (SEED)

- Criar usuarios padrao: sim, mas com politica segura.
- Criar roles e permissoes: sim, essencial para RBAC.
- Como garantir ambiente testavel:
  - migration unica e idempotente para seed base
  - credenciais iniciais via variavel (ou reset forcado no primeiro login)
  - separar seed de dev e seed de prod por profile ou scripts distintos

## 13. TESTE DE FUNCIONAMENTO EM PRODUCAO

Roteiro de validacao apos deploy:

1. API online
- GET /swagger-ui.html retorna 200
- GET /v3/api-docs retorna 200

2. Login funciona
- POST /auth/authenticate com usuario seed
- Resposta contem token JWT

3. Endpoints principais respondem
- endpoint protegido retorna 401 sem token
- endpoint admin retorna 403 para usuario sem role ADMIN
- endpoint admin retorna 200 para token com role ADMIN

## 14. PROBLEMAS POTENCIAIS NA MIGRACAO

Riscos principais:

- Falha de conexao com banco por DB_URL incorreta
- Porta incorreta por falta de PORT em server.port
- Variaveis ausentes (DB, JWT, MAIL)
- Build falhar por ausencia de cache/rede Maven
- Flyway falhar por schema/permissoes
- CORS bloquear frontend em dominio Railway
- Swagger aberto em producao sem controle de acesso

## 15. RESULTADO FINAL

Dockerfile final:

~~~dockerfile
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
RUN chmod +x mvnw || true
RUN ./mvnw -q -DskipTests dependency:go-offline
COPY src src
RUN ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/authentica-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
~~~

Lista final de variaveis de ambiente:

- PORT
- DB_URL
- DB_USER
- DB_PASSWORD
- JWT_SECRET
- MAIL_HOST
- MAIL_PORT
- MAIL_USER
- MAIL_PASSWORD
- SPRING_PROFILES_ACTIVE
- JAVA_OPTS

Ajustes necessarios no codigo/config:

- application.yml
  - server.port para ${PORT:8080}
  - spring.datasource.url para ${DB_URL}
  - spring.datasource.username para ${DB_USER}
  - spring.datasource.password para ${DB_PASSWORD}
  - api.security.token.secret para ${JWT_SECRET}
  - spring.mail.* para variaveis de ambiente
- SecurityConfig
  - trocar lista fixa de CORS por variavel configuravel
- Opcional de seguranca
  - restringir Swagger em producao por profile
  - remover fallback default de segredo JWT no codigo

Checklist pratico de deploy Railway:

1. Criar service no Railway apontando para este repositorio.
2. Adicionar plugin PostgreSQL Railway.
3. Configurar variaveis de ambiente da aplicacao.
4. Confirmar PORT mapeado em server.port por variavel.
5. Validar build Docker do projeto.
6. Subir deploy e acompanhar logs de startup.
7. Confirmar Flyway aplicou schema sem erro.
8. Testar /auth/authenticate com usuario seed.
9. Testar endpoint admin com token de ADMIN.
10. Ajustar CORS para dominio real do frontend.
11. Revisar exposicao de Swagger em producao.
12. Rotacionar credenciais iniciais apos primeiro acesso.
