# 🌟 Pokémon API

Uma API REST desenvolvida em Spring Boot que atua como um serviço de cache para dados de Pokémons. O projeto integra-se à PokeAPI (uma API externa) para buscar detalhes de Pokémons e armazena esses dados localmente em um banco de dados H2 (em memória) para acesso rápido e funcionalidade de favoritos.

## 🚀 Arquitetura do Projeto

O projeto segue a arquitetura de camadas padrão do Spring Boot:

* **Controller (`/controller`):** Gerencia as requisições HTTP, definindo os endpoints da API.
* **Service (`/service`):** Contém a regra de negócio principal, orquestrando a busca na PokeAPI e a manipulação dos dados no repositório.
* **Repository (`/repository`):** Interface que estende `JpaRepository` para interagir com o banco de dados H2.
* **Model (`/model`):** Contém a entidade `Pokemon` (mapeamento da tabela) e DTOs (Data Transfer Objects).
* **H2 Database:** Banco de dados em memória utilizado para persistir os Pokémons cacheados.

## ⚙️ Tecnologias Utilizadas

* Java 17+
* Spring Boot 3
* Spring Web (API REST)
* Spring Data JPA
* H2 Database (Banco de dados em memória)
* Lombok (Para reduzir código boilerplate)
* Gradle (Gerenciador de dependências)

## 🛠️ Como Configurar e Rodar o Projeto

Este projeto é uma API REST desenvolvida em Spring Boot e gerenciada pelo Gradle.

### Pré-requisitos

Certifique-se de que você tem instalado em sua máquina:
* **Java Development Kit (JDK) 17 ou superior.**

### Passo a Passo Resumido

1.  **Clone o Repositório:**
    Abra seu terminal e baixe o código-fonte:
    ```bash
    git clone [https://github.com/fefr7/pokemon_api1.git](https://github.com/fefr7/pokemon_api1.git)
    cd pokemon_api1
    ```

2.  **Execute a Aplicação:**
    Use o **Gradle Wrapper** (`./gradlew`) para compilar e iniciar a aplicação.
    ```bash
    ./gradlew bootRun
    ```
    > ℹ️ *A aplicação será iniciada e o servidor Tomcat estará rodando na porta `8080`.*

### Acessos Importantes

Após a aplicação iniciar (você verá a mensagem `Started PokemonApi1Application`), verifique:

| Recurso | URL | Finalidade |
| :--- | :--- | :--- |
| **Status da Aplicação** | `http://localhost:8080/actuator/health` | Confirma se o serviço está ativo (deve retornar `status: UP`). |
| **Console do H2** | `http://localhost:8080/h2-console` | Acesse o banco de dados em memória para visualizar dados. |

### Testando a API

Use ferramentas HTTP (como Postman ou cURL) para interagir com os endpoints, que começam em `/api/pokemon`.

**Exemplo de Cache (Criação de Registro):**

```bash
# Envia requisição POST para buscar 'bulbasaur' e salvar no H2
curl -X POST http://localhost:8080/api/pokemon/cache/bulbasaur
