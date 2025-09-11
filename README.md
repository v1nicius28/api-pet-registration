<img width="1493" height="626" alt="Capturar6" src="https://github.com/user-attachments/assets/8a2804eb-25ce-47a3-996b-9b0bec61b1b3" />
<img width="1498" height="694" alt="Capturar5" src="https://github.com/user-attachments/assets/c074d4a2-8b3a-48d9-a732-eb349ae4f5f8" />


# Pet-Registration-System-Sql

Este é um sistema de cadastro de pets desenvolvido em Java Spring Boot, integrado a um banco de dados SQL Server executado em contêiner via Docker Compose.
O objetivo do projeto é permitir o gerenciamento de informações de animais de estimação e perguntas relacionadas, utilizando uma API REST.

🚀 Tecnologias Utilizadas

O projeto foi construído com as seguintes tecnologias:

Java 17

Spring Boot

Spring Data JPA

SQL Server com Docker

Maven para gerenciamento de dependências

Docker e Docker Compose para orquestração

📂 Estrutura do Projeto

O projeto está organizado em módulos principais:

Controllers: responsáveis por receber as requisições da API e direcionar as ações.

Services: contêm as regras de negócio.

Repositories: fazem a comunicação com o banco de dados.

Models: representam as entidades como Pet, Endereço e Pergunta.

Configuração: arquivos de inicialização, propriedades e dependências.

⚙️ Configuração do Banco de Dados

O banco de dados utilizado é o SQL Server, configurado por meio do Docker Compose. O projeto já traz um arquivo de configuração que facilita a inicialização do banco em contêiner, sem a necessidade de instalação local.

▶️ Como Executar o Projeto

Clonar o repositório para a sua máquina.

Iniciar o banco de dados utilizando o Docker Compose.

Importante: É necessário que o Docker esteja instalado na máquina para que o Docker Compose funcione. Porém, não precisa ter o SQL Server instalado localmente, pois o banco roda dentro do container Docker.

Após o comando, a aplicação ficará disponível em um servidor local, pronta para receber requisições via Postman ou outro cliente REST.

📡 Funcionalidades Disponíveis

O sistema disponibiliza uma API com os seguintes recursos:

Pets: cadastrar, listar, atualizar, buscar por identificador e excluir pets.

Perguntas: cadastrar e listar perguntas relacionadas ao sistema.

🧪 Testes com Postman

É possível testar a API utilizando o Postman, enviando requisições para os endpoints.
Um exemplo simples é cadastrar um pet informando nome, tipo, sexo, idade e endereço. Da mesma forma, é possível consultar todos os pets já cadastrados ou editar informações de um específico.
