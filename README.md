# API de Cobranças
API cobranças com as seguintes capacidades:

- Envio de cobrança via carta, SMS, e-mail;
- Negativação de crédito junto aos birôs;
- Renegociação de dívidas
- Pagamento


## Setup
Rode o script a seguir dentro do diretório do projeto para verificar as dependências
```
./setup.sh
```

Este projeto depende do docker para rodar. O script não verifica se o docker está instalado.


## Iniciando a API em desenvolvimento
Executando um simples docker compose up é possível subir os serviços dos quais a aplicação depende em desenvolvimento.
São elas:
- Mysql: armazenamento de dados da api
- Redis: cache da api
- Maildev: servidor de email de desenvolvimento
- Kafka: comunicação assíncrona entre serviços
- Zookeeper: gerenciamento dos nodes do kafka
- Kafdrop: web-ui para visualização dos dados do kafka
