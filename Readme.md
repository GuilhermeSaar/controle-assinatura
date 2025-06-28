# 📦 Sistema de Controle de Assinaturas(Em desenvolvimento)

Este é um projeto desenvolvido como parte de um desafio técnico de backend com foco em **mensageria assíncrona**, usando **RabbitMQ** e **Spring Boot**. A proposta simula um sistema de controle de assinaturas com integração de webhooks, processamento de eventos em background e persistência em PostgreSQL.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- RabbitMQ (Mensageria)
- PostgreSQL
- Flyway (versionamento de banco)
- JPA / Hibernate
- Docker (para mensageria)
- Jackson (serialização JSON)

---

## 📌 Funcionalidades Implementadas

### 1. 👤 Criação de Assinaturas
- Endpoint: `POST /api/subscriptions`
- A assinatura é criada com status `PENDENTE`.
- Um evento `SUBSCRIPTION_CREATED` é enviado para a fila do RabbitMQ.
- O Worker consome o evento e simula o envio ao sistema de cobrança.

### 2. 💳 Webhook de Pagamento
- Endpoint: `POST /api/webhooks/payment`
- Recebe um evento externo de pagamento.
- Envia para a fila um evento `PAYMENT_SUCCESS`.
- O Worker consome o evento e ativa a assinatura no banco.

### 3. 🔄 Processamento Assíncrono
- Um Worker escuta os eventos da fila e processa as mudanças no banco.
- Os eventos processados são marcados como `processed = true` na tabela `tb_events`.

---

## 📊 Tabelas Envolvidas

- `tb_plans`: planos disponíveis.
- `tb_subscriptions`: informações das assinaturas.
- `tb_events`: log de eventos processados.
- Tipos ENUM: `billing_cycle_enum`, `subscription_status`, `event_type_enum`.

---

## Testando as Funcionalidades

### Criar assinatura

Envie uma requisição `POST` para `api/subscriptions` com o corpo:

```json
{
  "planId": "11111111-1111-1111-1111-111111111111",
  "customerEmail": "cliente@email.com"
}
```

### Pagamento

Envie uma requisição `POST` para `/api/webhooks/payment` com o corpo:

```json
{
  "subscriptionId": "eded5e5b-cf1f-4198-bbbc-39e0d2adfcc3",
  "event": "payment_success",
  "amount": 29.99,
  "date": "2025-01-01"
}
```

### Obter métricas de assinatura

Envie uma requisição `GET` para `/api/reports/subscriptions`


## ❗ Desafios enfrentados

> Compartilho aqui as principais dificuldades que enfrentei:

### 🧩 Entender o fluxo do RabbitMQ
No início, foi confuso compreender o caminho completo: **Producer → Exchange → Queue → Consumer**, mas com prática e leitura de logs consegui entender cada etapa do fluxo.

### 🧱 Integração com o banco via Flyway
Eu tinha o costume de deixar o JPA criar as tabelas automaticamente. Trabalhar com **migrações via SQL** foi um desafio, mas agora entendo melhor como manter versões seguras e rastreáveis do banco.

### 🐛 JSON no PostgreSQL
A coluna `data` na tabela de eventos era do tipo JSON. Precisei usar a anotação `@JdbcType(JsonJdbcType.class)` e configurar a dependência do **Hibernate Types** para funcionar corretamente.

### 🕵️‍♂️ Erros de UUID e campos nulos
Alguns erros de serialização aconteceram, erros simples, mas aprendi na prática a validar e formatar corretamente os dados.

---


## 🔗 Referência do Desafio

Este projeto foi desenvolvido com base no desafio técnico publicado por [Rafael Coelho](https://racoelho.com.br), disponível em:

👉 **[https://racoelho.com.br/listas/desafios/controle-de-assinaturas](https://racoelho.com.br/listas/desafios/controle-de-assinaturas)**

---

## 👨‍💻 Autor

Desenvolvido por **Guilherme Saar** — 2025

Se você chegou até aqui, obrigado por ler! 🚀