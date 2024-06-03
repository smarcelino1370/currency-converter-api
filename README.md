# Currency Converter REST API
## Apresentação
Este projeto consiste em uma API REST para conversão de moedas, utilizando taxas de conversão atualizadas de um serviço externo. A aplicação permite que os usuários convertam valores entre duas moedas diferentes, registrando cada transação realizada. Além disso, disponibiliza um endpoint para consulta das transações realizadas por um usuário específico.

## Propósito
O propósito desta API é fornecer uma solução simples e eficiente para realizar conversões monetárias, garantindo que as taxas de câmbio utilizadas estejam sempre atualizadas. Além disso, a funcionalidade de registro de transações permite aos usuários acompanhar seu histórico de conversões.

## Features
- Autenticação de Usuário.
- Registro de novo Usuário.
- Conversão de moedas utilizando taxas de câmbio atualizadas.
- Consulta de Transações realizadas por um Usuário.

## Tecnologias Utilizadas
- **Java 17**: Escolhido pela sua performance, novas funcionalidades e suporte estendido.
- **Spring boot**: Framework para facilitar a construção de aplicações Java, proporcionando configuração mínima e rápida inicialização.
- **H2 Database**: Banco de dados em memória, escolhido para simplificar o desenvolvimento e os testes da aplicação.
- **Spring Data JPA**: Facilita o acesso e a manipulação de dados com o banco de dados, utilizando o padrão JPA (Java Persistence API).
- **Spring Web**: Módulo do Spring Boot para desenvolvimento de aplicações web, utilizado para criar e expor os endpoints REST.
- **Spring Security**: Módulo do Spring Boot para implementação da segurança de aplicações.

## Executando a Aplicação
- **Compilação do Projeto**: Utilize o Maven para compilar o projeto com o comando mvn clean package.
- **Execução do JAR**: Execute o JAR gerado na pasta target com o comando java -jar currency-converter-api.jar.
- **Acessando a API**: A API estará disponível em http://localhost:8080/api.

## Aplicação pode ser observada em 
- https://currencyconverterapi-staging-4ec985f3b646.herokuapp.com/swagger-ui/index.html

## Endpoints
### Sign In
- **Endpoint**: `v1/auth/signin`
- **Método**: `POST`
- **Corpo da Requisição**:
  ```json
  {
    "username": "admin",
    "password": "admin123"
  }
  ```
- **Resposta**: Retorna um access token. Nos demais endpoints deverá disponibilizar o mesmo através do cabeçalho Authorization.

### Registrar um novo Usuário
- **Endpoint**: `v1/user`
- **Método**: `POST`
- **Corpo da Requisição**:
  ```json
  {
    "username": "admin",
    "password": "admin123",
    "roles": [ "ADMIN" ]
  }
  ```
- **Resposta**: Retorna o id do Usuário Registrado.

### Listar todos os Usuários
- **Endpoint**: `v1/user`
- **Método**: `GET`
- **Resposta**: Retorna uma lista com todos os Usuários cadastrados.
### Conversão de Moedas

- **Endpoint**: `v1/conversion/convert/{userId}`
- **Método**: `POST`
- **Corpo da Requisição**:
  ```json
  {
    "origin": "BRL",
    "destination": "USD",
    "amount": 100
  }
  ```
- **Resposta**: Retorna o valor convertido e salva a transação no banco de dados.

### Consulta de Transações por Usuário

- **Endpoint**: `v1/conversion/{userId}`
- **Método**: `GET`
- **Resposta**: Retorna todas as transações realizadas pelo usuário especificado.

## Estrutura do Projeto
O projeto segue o padrão de arquitetura limpa, com a separação de responsabilidades em camadas:

- **Adapters/Controllers**: Responsáveis por receber as requisições HTTP, processá-las e retornar as respostas adequadas.
- **Use Cases**:  Contêm as regras de negócio da aplicação, representando as principais funcionalidades.
- **Models**: Representam os objetos de negócio da aplicação.
- **Repositories**: Interfaces que estendem a Repository do Spring Data JPA, responsáveis por realizar operações de acesso aos dados no banco de dados.
- **Infra**: Prove a infraestrutura da aplicação, bem como implementação dos contrato de comunicação com recursos externos.

---