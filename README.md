## API RESTful de E-commerce com Sistema de Afiliados



- Nesse projeto Java utilizei o Spring Boot e alguns conceitos de SOLID para criar sozinho uma API RESTful que simula um e-commerce de afiliados.

<br>

# Tecnologias usadas

### Lombok

- Utilizei o Lombok para diminuir a geração e repetição de código boilerplate, visando deixar as classes mais legíveis.

### Spring Data JPA
- Utilizei o Spring Data JPA para fazer interação com o banco de dados.

### Spring Web
- Utilizei o Spring Web para incorporar um servidor Tomcat, permitindo que a aplicação seja implantada e executada diretamente, tornando-a autossuficiente.

### Spring Security com JWT
- Utilizei o Spring Security com JWT para tratar a autenticação e autorização da aplicação.

### Swagger
- Utilizei o Swagger para documentar a API e facilitar a visualização dos endpoints.

<br>


# Funcionamento da aplicação

- É possivel criar,logar, editar, listar e filtrar usuarios.
- É possivel criar, editar, comprar, avaliar, listar e filtrar produtos.
- É possivel listar e filtrar vendas.


# Tipos de Usuários

### Administrador:

- Uma vez cadastrado e logado, o administrador tem acesso e controle total sobre a aplicação.
- Exceto pela criação, edição, compra, avaliação e inativação de produtos, que são direcionadas apenas aos vendedores.

### Vendedor:

- Uma vez cadastrado e logado, o vendedor pode criar produtos.
- Pode editar apenas os produtos criados por ele.
- Pode listar e filtrar apenas os produtos criados por ele.
- Pode listar e filtrar apenas as vendas feitas por ele.
- Pode listar e filtrar apenas as avaliações feitas ao produto dele.

### Usuário Comum:

- Deslogado, pode apenas listar e filtrar todos os produtos disponíveis.
- Uma vez cadastrado e logado, pode listar e filtrar todos os produtos.
- Pode adicionar produtos ao carrinho.
- Pode comprar produtos e avaliar os produtos comprados.
- Pode listar e filtrar seu histórico de compras.

<br>

# Pré-requisitos

- Java 14 ou superior para uso dos Records
- Banco de dados MySQL criado com o nome definido no application.properties
- Ter o Lombok adicionado a sua IDE, não basta apenas adicionar a dependencia no pom.xml


