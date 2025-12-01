# Controle de Estoque e Módulo de Vendas
Este repositório contém uma API REST desenvolvida em Spring Boot para gerenciamento de controle de estoque, que foi estendida para incorporar um módulo de Vendas. O projeto implementa lógica de negócios crítica, como a baixa automática e transacional do estoque após o registro de uma venda.

A base do código foi desenvolvida como parte de um projeto de extensão, com a implementação do módulo de Vendas e a lógica de transação como principal desafio adicional concluído.

---

As tecnologias usadas foram:
- Java
- Spring Boot
- Spring Data JPA / Hibernate
- Maven
- MySQL8

---
 
Para clonar o repositório será necessário o seguinte comando:

	https://github.com/Pyetro7007/DS_ControleEstoqueAPI_PyetroFabricioPeterlini.git
 
Após clonar, será necessário acessar a pasta DS_ControleEstoqueAPI_PyetroFabricioPeterlini com o comando:

 	cd DS_ControleEstoqueAPI_PyetroFabricioPeterlini

Como este projeto utiliza Maven, a instalação das dependências geralmente ocorre automaticamente durante o build, mas você pode forçar o download e build com:

  ` mvn clean install ` 

---

**Configuração do Banco de Dados**

Edite o arquivo application.properties (localizado em src/main/resources) para configurar suas credenciais (dialeto) e URL do banco de dados, neste projeto foi utilizado o MySQL8.

---

Para iniciar o servidor Spring Boot, use o comando:

` mvn spring-boot:run `

O servidor da API estará disponível em http://localhost:8080 (porta padrão do Spring Boot).

---

**Endpoints**

A API é estruturada em torno de quatro controladores principais: **Vendas**, **Clientes**, Categorias, Fornecedores e **Produtos** (Estoque).

### 1. Módulo de Vendas (Lógica Transacional Crítica)

O endpoint de registro de venda é o mais importante, pois aciona a lógica de **verificação e baixa de estoque de forma transacional**.

| Método HTTP | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/vendas` | **Registra uma nova venda.** Recebe o `clienteId` e a lista de `itens`. Realiza a baixa do estoque e garante o *rollback* em caso de falha. |
| `GET` | `/api/vendas` | Lista todas as vendas registradas. |
| `GET` | `/api/vendas/{id}` | Busca os detalhes de uma venda específica. |
| `DELETE` | `/api/vendas/{id}` | Remove uma venda. |

### 2. Módulo de Clientes (CRUD)

| Método HTTP | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/clientes` | **Cria** um novo cliente. |
| `GET` | `/api/clientes` | Lista todos os clientes cadastrados. |
| `GET` | `/api/clientes/{id}` | Busca um cliente específico por ID. |
| `PUT` | `/api/clientes/{id}` | **Atualiza** os dados de um cliente existente. |
| `DELETE` | `/api/clientes/{id}` | Remove um cliente. |

### 3. Módulo de Produtos (Estoque)

| Método HTTP | Endpoint | Descrição |
| :--- | :--- | :--- |
| `POST` | `/api/produtos` | **Cria** um novo produto, incluindo seu registro inicial de estoque. |
| `GET` | `/api/produtos` | Lista todos os produtos (incluindo informações de estoque). |
| `GET` | `/api/produtos/{id}` | Busca um produto específico por ID. |
| `PUT` | `/api/produtos/{id}` | **Atualiza** os dados de um produto existente. |
| `DELETE` | `/api/produtos/{id}` | Remove um produto. |

### 4. Entidades de Suporte (Categorias e Fornecedores)

| Módulo | Método HTTP | Endpoint | Descrição |
| :--- | :--- | :--- | :--- |
| **Categorias** | `GET`/`POST`/`PUT`/`DELETE` | `/api/categorias` | CRUD para o gerenciamento de categorias de produtos. |
| **Fornecedores** | `GET`/`POST`/`PUT`/`DELETE` | `/api/fornecedores` | CRUD para o gerenciamento de fornecedores. |

---

Para mais informações, segue um vídeo mostrando algumas das funcionalidades da API: 
