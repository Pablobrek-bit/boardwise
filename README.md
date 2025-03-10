# BoardWise API 🚀

![CI Status](https://github.com/<seu-user>/<seu-repo>/workflows/Java%20CI/badge.svg)

[![Java](https://img.shields.io/badge/Java-21%2B-orange)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)
[![Swagger](https://img.shields.io/badge/Documentação-Swagger-%2385EA2D)](http://localhost:8080/swagger-ui.html)

Uma API RESTful para o gerenciamento de projetos no estilo Kanban, inspirada em ferramentas como Trello e Jira. Gerencie projetos, listas, cartões (tarefas), comentários e muito mais!

---

## 📋 Tabela de Conteúdos
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Instalação](#-instalação)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Contribuição](#-contribuição)
- [Licença](#-licença)

---

## 🌟 Funcionalidades

### **Funcionalidades Principais**
- **Projetos**: CRUD de projetos com membros e permissões.
- **Listas**: Estágios personalizáveis (ex: "A Fazer", "Concluído").
- **Cartões (Tarefas)**: Atribuição a usuários, prazos, prioridades e movimentação entre listas.
- **Autenticação**: JWT para login seguro e controle de acesso.
- **Comentários**: Discussões em tempo real nos cartões.
- **Anexos**: Upload de arquivos para AWS S3/Google Cloud Storage.
- **Notificações**: Alertas por e-mail para mudanças importantes.

### **Recursos Avançados**
- 🔍 Busca e filtros de cartões por título, responsável ou prazo.
- 📊 Histórico de atividades (ex: "Cartão X movido por João").
- ⚡ Atualizações em tempo real via WebSocket.
- 🧩 Templates de projetos pré-definidos (ex: Scrum, Marketing).

---

## 🛠️ Tecnologias Utilizadas
| Categoria          | Tecnologias                                             |
|---------------------|---------------------------------------------------------|
| **Backend**         | Java 21, Spring Boot 3.x, Spring Data JPA, Spring Security |
| **Banco de Dados**  | PostgreSQL                                              |
| **Autenticação**    | JWT                 |
| **Armazenamento**   | AWS S3 / Google Cloud Storage (anexos)                  |
| **Documentação**    | Swagger/OpenAPI                                         |
| **Testes**          | JUnit 5, Mockito, Testcontainers                        |

---

## 🚀 Instalação

### Pré-requisitos
- Java 21+
- Maven
- PostgreSQL
- Conta em AWS S3/Google Cloud (opcional para anexos)

### Passo a Passo
1. **Clone o repositório**
   ```bash
   git clone
    ```
2. **Crie um banco de dados no PostgreSQL**
3. **Configure o banco de dados no arquivo `application.properties`**
    ```bash
    spring.datasource.url=jdbc:postgresql://localhost:5432/boardwise
    spring.datasource.username=postgres
    spring.datasource.password=secret
    ```
4. **Configure as variáveis de ambiente (veja [Variáveis de Ambiente](#-variáveis-de-ambiente))**
5. **Compile e execute**
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```
6. **Acesse a documentação da API em `http://localhost:8080/swagger-ui.html`**
7. **Pronto! A API está rodando em `http://localhost:8080`**
8. **Para parar a execução, pressione `Ctrl + C`**
9. **Para rodar os testes, execute `mvn test`**
10. **Para gerar um JAR, execute `mvn package`**

---
## 🔧 Variáveis de Ambiente
Crie um arquivo `.env` na raiz do projeto ou configure no application.properties.

```bash
# Configurações do banco de dados
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/boardwise
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=secret

# Configurações do JWT
JWT_SECRET=SUA_SENHA_SECRETA
JWT_EXPIRATION=86400000 # 24 horas
```

---
## 🤝 Contribuição
1. Faça um fork do projeto
2. Crie uma nova branch (`git checkout -b feature/nova-feature`)
3. Faça um commit das mudanças (`git commit -am 'Adiciona nova feature'`)
4. Faça um push para a branch (`git push origin feature/nova-feature`)
5. Crie um novo Pull Request

---
## 📝 Licença
Distribuído sob a licença MIT. Veja `LICENSE` para mais informações.





