# 💰 Finanças API

API REST para gerenciamento de finanças pessoais desenvolvida com Java e Spring Boot.

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-prod-blue)
![Railway](https://img.shields.io/badge/Deploy-Railway-purple)

## 🚀 Deploy

👉 **[API em produção](https://financas-api-production-5172.up.railway.app/api/transacoes)**

👉 **[Documentação Swagger](https://financas-api-production-5172.up.railway.app/swagger-ui/index.html)**

## 🛠️ Tecnologias

- Java 21
- Spring Boot 4.0.3
- Spring Data JPA
- Spring Validation
- PostgreSQL (produção) / H2 (desenvolvimento)
- Swagger/OpenAPI
- Maven
- Deploy no Railway

## 📋 Endpoints

| Método | URL | Descrição |
|--------|-----|-----------|
| GET | `/api/transacoes` | Lista todas as transações |
| GET | `/api/transacoes/{id}` | Busca transação por ID |
| POST | `/api/transacoes` | Cria nova transação |
| PUT | `/api/transacoes/{id}` | Atualiza transação |
| DELETE | `/api/transacoes/{id}` | Remove transação |
| GET | `/api/transacoes/tipo/{tipo}` | Filtra por tipo (RECEITA/DESPESA) |
| GET | `/api/transacoes/periodo?inicio=&fim=` | Filtra por período |
| GET | `/api/transacoes/categoria/{categoria}` | Filtra por categoria |
| GET | `/api/transacoes/resumo` | Resumo financeiro (saldo, receitas, despesas) |

## 📦 Exemplo de requisição
```json
POST /api/transacoes
{
    "descricao": "Salario mensal",
    "valor": 5000.00,
    "data": "2026-03-01",
    "tipo": "RECEITA",
    "categoria": "Trabalho"
}
```

## ⚙️ Como executar localmente
```bash
git clone https://github.com/GabrielVeras-dev/financas-api.git
cd financas-api
mvn spring-boot:run
```

Acesse: `http://localhost:8080/api/transacoes`

H2 Console: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:financasdb`
- User: `sa`