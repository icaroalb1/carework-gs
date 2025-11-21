#!/bin/bash
# Script para executar a aplicação com o profile local (que contém a chave da OpenAI)

./mvnw spring-boot:run -Dspring-boot.run.profiles=local

