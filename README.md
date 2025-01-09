# Microsserviço Course (o Projeto ainda está sendo desenvolvido)

O microsserviço **Course** é responsável por gerenciar os cursos oferecidos na plataforma de estudos. Ele é uma parte crucial da arquitetura da plataforma, permitindo que os alunos acessem informações detalhadas sobre os cursos e acompanhando seu progresso ao longo do tempo.


# Tecnologias Utilizadas (até o momento)
- **Java 17**: Linguagem de programação usada para o desenvolvimento do microsserviço.
- **Spring Boot 3.4.0**: Framework utilizado para facilitar o desenvolvimento e configuração do microsserviço.
- **Spring Data JPA**: Utilizado para interagir com o banco de dados relacional de maneira simplificada, permitindo a realização de operações CRUD.
- **PostgreSQL**: Banco de dados relacional utilizado para armazenar as informações dos usuários e dados da aplicação.
- **Spring Boot Starter Web**: Dependência que habilita a criação de serviços web RESTful, permitindo a comunicação entre o microsserviço e o cliente.
- **Spring Boot Starter Test**: Dependência usada para realizar testes automatizados no microsserviço, garantindo a qualidade do código.
- **Spring Boot Starter Validation**: Usado para validação de dados de entrada, como o cadastro de usuários, garantindo que as informações fornecidas estejam corretas.


## Funcionalidades
**Funcionalidades principais:**
- Cadastro de cursos.
- Cadastro de módulos e lições.
- Acompanhamento do progresso do aluno nos cursos.


## Endpoints Principais
**Curso**
- `POST /COURSE`: Realiza a Criação de um novo Curso na plataforma
- `GET /ALL COURSES`: Obtém as informações de um determinado Curso
- `GET /ONE COURSE{id}`: Obtém as informações de um Curso específico.
- `PUT /UPDATE COURSE`: Atualiza os dados de um Curso específico.
- `DELETE /COURSE`: Exclui um Curso

**Modulo**
- `POST /MODULE`: Realiza a Criação de um novo Module na plataforma
- `GET /ALL MODULES`: Obtém as informações de um determinado Modulo
- `GET /ONE MODULE{id}`: Obtém as informações de um Module específico.
- `PUT /UPDATE MODULE`: Atualiza os dados de um Modulo específico.
- `DELETE /MODULE`: Exclui um determinado Module

**Lessons**
- `POST /LESSONS`: Realiza a Criação de uma nova Lição na plataforma
- `GET /ALL LESSONS`: Obtém as informações de uma determinada Lição.
- `GET /ONE LESSON{id}`: Obtém as informações de uma Lição específica.
- `PUT /UPDATE LESSON`: Atualiza os dados de uma Liçao específico.
- `DELETE /LESSON`: Exclui uma Lição

Essas funcionalidades formam a base do gerenciamento de cursos, módulos e lições na plataforma de estudos. Cada uma delas possibilita a criação, leitura, atualização e exclusão de cursos e seus conteúdos, garantindo que os administradores possam facilmente organizar e manter o conteúdo educacional disponível para os alunos.




