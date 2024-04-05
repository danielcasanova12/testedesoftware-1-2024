# Bank API

### Ambiente de Desenvolvimento

Para utilizar e desenvolver sobre este projeto, instalar as seguintes ferramentas:

 - Java Development Kit (JDK), versão 17 ou superior.
 - Visual Studio Code.
   - Instalar as seguintes extensões:
     - Extension Pack for Java;
     - Spring Boot Extension Pack;
     - Rest Client (Cliente para fazer requisições HTTP);
     - Thunder Client (Cliente para fazer requisições HTTP).

### Banco de Dados

 - Necessário utilizar um SGDB tal como o PostgreSQL ou MariaDB. 
 - O projeto contém 2 arquivos <code>application.properties</code> de configuração pré-configurados para MariaDB (padrão) e PostgreSQL. Os arquivos estão em <code>main/resources</code>

### Executando o projeto

A classe principal do projeto está localizada em <code>main/java/br/edu/utfpr/bankapi/Application.java</code>.

A API estará apta à receber requisições no endereço http://localhost:8080.
