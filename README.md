# Batalha Medieval

Este projeto consiste de uma API desenvolvida para realização de um jogo de batalha medieval.



## Tecnologias

Esta API Rest está desenvolvida em Java em sua versão <a href="https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html">8</a> com <a href="https://spring.io/projects/spring-boot">Spring Boot 2</a>, utilizando <a href="https://www.mongodb.com/">MongoDB</a> como repositório de dados

## Instalação

[![Docker Logo](https://d207aa93qlcgug.cloudfront.net/1.95.5.qa/img/nav/docker-logo-loggedout.png)](https://hub.docker.com/r/marcosmele/batalha-medieval/)

Utilize o arquivo docker-compose.yml 

`docker-compose up --build`

Este arquivo irá utilizar a imagem docker mais recente da API subindo em sua porta `8080`, e subir juntamente com o Mongo DB.

## Documentação

Funcionalidades:

* Criar Batalha
* Listar Heróis
* Escolher Herói
* Definir ataque (iniciativa)
* Realizar ataque (turno)
* Visualizar Ranking

A documentação da API pode ser melhor visualizada de forma iterativa através do <a href="http://localhost:8080/swagger-ui.html">Swagger</a> assim que a aplicação estiver no ar.

Existe um projeto de exemplo em  <a href="https://github.com/marcosmele/batalha-medieval-client">Batalha Medieval Client</a> onde é possível observar o processo encadeado da API.

A descrição do processo pode ser visualizada no arquivo descritivo `Desafio-Desenvolvedor-RPG-Battle.pdf`

## Desenvolvimento

O projeto foi desenvolvido na IDE <a href="https://www.eclipse.org/">Eclipse</a> utilizando o padrão Maven para gerenciamento de dependências.

Ao realizar qualquer modificação, tenha cuidado como serão afetados os testes e a cobertura do código.
Para subir um ambiente local existem 2 possibilidades:


1.  Rode utilizando o arquivo `docker-compose-dev.yml` que irá gerar uma imagem local e subir juntamente com um servidor MongoDB.
2.  Basta rodar a aplicação no eclipse utilizando o spring profile de desenvolvimento: `-Dspring.profiles.active=dev`. Para isso será necessário que haja um servidor mongo configurado em `application-dev.yml` (Por padrão utilizará a base de dados local:27017)  

Utilizando o arquivo `Postman_Collection.json` é possível testar a API no <a href="https://www.getpostman.com/">Postman</a>.