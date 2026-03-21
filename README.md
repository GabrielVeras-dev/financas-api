# 💰 Finanças API

API REST para gerenciamento de finanças pessoais, desenvolvida com Java e Spring Boot.

## 🚀 Tecnologias

- Java 21
- Spring Boot 4.0.3
- Spring Data JPA
- Spring Validation
- H2 Database (desenvolvimento)
- PostgreSQL (produção)
- Maven

## 🧱 Arquitetura

O projeto segue a arquitetura em camadas:
```
Controller → Service → Repository → Model
```

- **Controller** — recebe as requisições HTTP e devolve respostas
- **Service** — contém as regras de negócio
- **Repository** — responsável pela comunicação com o banco de dados
- **Model** — representa as entidades do banco de dados
- **DTO** — objetos de transferência de dados entre as camadas

## 📋 Funcionalidades

- ✅ Cadastro de transações financeiras (receitas e despesas)
- ✅ Listagem de todas as transações
- ✅ Busca de transação por ID
- ✅ Atualização de transações
- ✅ Exclusão de transações
- ✅ Filtro por tipo (RECEITA ou DESPESA)
- ✅ Filtro por período (data início e data fim)

## 🔗 Endpoints

### Transações

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/transacoes` | Lista todas as transações |
| GET | `/api/transacoes/{id}` | Busca transação por ID |
| POST | `/api/transacoes` | Cria uma nova transação |
| PUT | `/api/transacoes/{id}` | Atualiza uma transação |
| DELETE | `/api/transacoes/{id}` | Remove uma transação |
| GET | `/api/transacoes/tipo/{tipo}` | Filtra por tipo (RECEITA ou DESPESA) |
| GET | `/api/transacoes/periodo?inicio=&fim=` | Filtra por período |
| GET | `/api/transacoes/categoria/{categoria}` | Filtra por categoria |

### Exemplo de requisição (POST /api/transacoes)
```json
{
    "descricao": "Salário",
    "valor": 5000.00,
    "data": "2026-03-19",
    "tipo": "RECEITA",
    "categoria": "Trabalho"
}
```

### Exemplo de resposta
```json
{
    "id": 1,
    "descricao": "Salário",
    "valor": 5000.00,
    "data": "2026-03-19",
    "tipo": "RECEITA",
    "categoria": "Trabalho"
}
```

## ⚙️ Como rodar localmente

### Pré-requisitos
- Java 21+
- Maven

### Passos
```bash
# Clone o repositório
git clone https://github.com/GabrielVeras-dev/financas-api.git

# Entre na pasta
cd financas-api

# Rode o projeto
./mvnw spring-boot:run
```

A API estará disponível em `http://localhost:8080`

O console do H2 estará disponível em `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:financasdb`
- User: `sa`
- Password: *(vazio)*

## 🌐 Deploy

A aplicação está hospedada no Railway:

👉 **[Link da API em produção](https://financas-api-production-5172.up.railway.app/api/transacoes)**


## 👨‍💻 Autor

**Gabriel Veras** — Desenvolvedor Java Júnior | Backend com Java e Spring Boot

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/dev-gabrielveras/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/GabrielVeras-dev)
[![Portfolio](https://img.shields.io/badge/Portfolio-FF5722?style=for-the-badge&logo=google-chrome&logoColor=white)](https://portfolio-gabrielveras-dev.vercel.app/)
