# BoardWise API 🚀

[![Java](https://img.shields.io/badge/Java-21%2B-orange)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.4-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)
[![Swagger](https://img.shields.io/badge/Documentação-Swagger-%2385EA2D)](http://localhost:8080/swagger-ui.html)

Uma API RESTful para gerenciamento de projetos no estilo Kanban, inspirada em ferramentas como Trello e Jira. Gerencie projetos, listas, cartões (tarefas), comentários e muito mais!

---

## 📋 Tabela de Conteúdos
- [Funcionalidades](#-funcionalidades)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Instalação](#-instalação)
- [Documentação da API](#-documentação-da-api)
- [Variáveis de Ambiente](#-variáveis-de-ambiente)
- [Como Usar](#-como-usar)
- [Contribuição](#-contribuição)
- [Licença](#-licença)
- [Roadmap](#-roadmap)

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
| Categoria          | Tecnologias                                                                 |
|---------------------|----------------------------------------------------------------------------|
| **Backend**         | Java 17, Spring Boot 3.x, Spring Data JPA, Spring Security                 |
| **Banco de Dados**  | PostgreSQL, Redis (cache)                                                  |
| **Autenticação**    | JWT, OAuth2 (opcional para login social)                                  |
| **Armazenamento**   | AWS S3 / Google Cloud Storage (anexos)                                     |
| **Documentação**    | Swagger/OpenAPI                                                            |
| **Testes**          | JUnit 5, Mockito, Testcontainers                                           |

---

## 🚀 Instalação

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL
- Redis (opcional para cache)
- Conta em AWS S3/Google Cloud (opcional para anexos)