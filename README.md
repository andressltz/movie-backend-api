# API de intervalo de prêmios

A aplicação retorna o produtor com menor intervalo entre dois prêmios consecutivos e o produtor que houver maior tempo entre prêmios consecutivos. Caso ocorra empate, serão retornados todos os que possuem o mesmo intervalo. 

## Requisitos

Java 17

## Como executar a aplicação

A aplicação pode ser executada por linha de comandos ou por um IDE. 

Para executar pelo IntelliJ, importe o projeto como um projeto Maven apontando para o arquivo `pom.xml`, dentro do IDE certifique-se de que esteja configurado com o JDK 17.

Execute a classe `Application.java`. 

Para executar por linha de comando, entre na pasta raiz do projeto e executo o comando do Maven:
```
cd movie-backend-api
mvn clean install
mvn spring-boot:run
```

Após inicializar a aplicação, acesse http://localhost:8080/intervals ou execute GET na URL http://localhost:8080/intervals para consumir o serviço. 

### Alterando os dados de entrada

O projeto atual esta utilizando um arquivo CSV para importar os dados de entrada. Este arquivo pode ser encontrado na pasta `src/main/resources/import/Movielist.csv`.

Para alterar o arquivo, pode ser substituindo o arquivo `Movielist.csv` pelo novo, mas observar a grafia do nome. Ou então, o mais recomendado é adicionar o arquivo na pasta `src/main/resources/import` e alterar o nome do CSV no arquivo `application.properties`.

Altere os valores padrão para os novos valores de entrada:
```
[...]
file.path=src/main/resources/import/    # Pasta aonde se encontra o arquivo CSV
file.name=Movielist.csv                 # Nome do arquivo CSV
file.splitter=;                         # Caractere utilizado para separar os campos 
file.header=true                        # Indica se possui cabeçalho ou se a primeira linha já é de dados (true se houver cabeçalho, false se não houver)
```

## Como executar os testes de integração

Os testes podem ser executados por linha comando ou por um IDE executando a classe `IntegrationTest.java`.

Para executar por linha de comando, entre na pasta raiz do projeto e executo o comando do Maven:
```
cd movie-backend-api
mvn clean install
mvn test
```
