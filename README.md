
# 📂 Microsserviço de Processos Judiciais

Este projeto é um microsserviço desenvolvido em Spring Boot com banco de dados PostgreSQL. Ele permite gerenciar números de processos judiciais, incluindo operações de cadastro, consulta, exclusão e associação de réus a processos já cadastrados.

## 🚀 Funcionalidades

### 🔢 Cadastrar número de processo
- **Endpoint**: `POST /processos`
- O número do processo deve ser único.
- Retorna erro ao tentar cadastrar um número de processo duplicado.

### 🔍 Consultar processos cadastrados
- **Endpoint**: `GET /processos`
- Retorna uma lista de todos os processos salvos.

### ❌ Excluir número de processo
- **Endpoint**: `DELETE /processos/{id}`
- Exclui um processo específico pelo seu id.

### 👤 Adicionar réu a um processo
- **Endpoint**: `POST /processos/{id}/reus`
- Adiciona um réu ao processo identificado pelo id.

## 🛠️ Tecnologias Utilizadas
- ⚙️ Spring Boot 3.4.2
- 🐘 PostgreSQL 17.4
- 🏗️ Liquibase (para controle de versões do banco de dados)
- 🔗 JPA/Hibernate (para mapeamento objeto-relacional)
- 🔍 JUnit 5 (para testes unitários)
- 🧪 TestRestTemplate (para testes de integração)

## 📑 Instalação e Configuração

### ✅ Pré-requisitos
- Java 21
- PostgreSQL 17+
- Maven 3.6+

### 🔧 Configurando o Banco de Dados
Crie um banco de dados no PostgreSQL:

```sql
CREATE DATABASE processos_db;
```

Atualize as credenciais de acesso ao banco em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/processos_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

O banco será automaticamente atualizado pelo Liquibase ao iniciar o projeto. Caso necessário, execute o script SQL manualmente (`database.sql`).

### 🏃 Como Executar o Projeto
Clone o repositório:

```bash
git clone https://github.com/seu-usuario/processos-microservice.git
cd processos-microservice
```

Compile e execute o projeto:

```bash
mvn spring-boot:run
```

Acesse o serviço via `http://localhost:8080`.

## 📝 Endpoints da API

### 🔢 Cadastrar Processo
**Endpoint:** `POST /processos`  
**Content-Type:** `application/json`

```json
{
  "numero": "1234567890"
}
```
**Resposta:**
- `201 Created` → Processo cadastrado com sucesso.
- `400 Bad Request` → Número de processo já existente.

### 🔍 Consultar Processos
**Endpoint:** `GET /processos`

**Resposta:**
```json
[
  {
    "id": 1,
    "numero": "1234567890",
    "reus": []
  }
]
```

### ❌ Excluir Processo
**Endpoint:** `DELETE /processos/{id}`

**Resposta:**
- `204 No Content` → Processo excluído com sucesso.
- `404 Not Found` → Processo não encontrado.

### 👤 Adicionar Réu a um Processo
**Endpoint:** `POST /processos/{id}/reus`  
**Content-Type:** `application/json`

```json
{
  "nome": "João da Silva"
}
```

**Resposta:**
- `201 Created` → Réu adicionado com sucesso.
- `404 Not Found` → Processo não encontrado.

### 🔍 Testes
Execute os testes usando o Maven:

```bash
mvn test
```

## 📄 Licença
Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para mais detalhes.

## 🙋 Contribuição
Contribuições são bem-vindas!

1. Faça um fork do projeto
2. Crie uma branch (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas alterações (`git commit -m 'Adicionando nova funcionalidade'`)
4. Envie um push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request 🚀

Desenvolvido por Michelle Luz Rodrigues 💻
