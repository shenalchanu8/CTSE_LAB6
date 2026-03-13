# Microservices Kafka Lab

Lightweight local microservices example using Kafka (KRaft) and an API Gateway.

## Contents
- `docker-compose.yml` — brings up Kafka (KRaft), Schema Registry, Kafka UI, API Gateway and three services (`order-service`, `inventory-service`, `billing-service`).
- Kaffka-Example/postman/Kafka-Microservices.postman_collection.json — Postman collection for quick tests.
- Kaffka-Example/postman/Kafka-Microservices.postman_environment.json — Postman environment (baseUrl).

## Requirements
- Docker (Desktop) and Docker Compose (v2+). Docker must be running.

## Run the stack
1. Open a terminal in the project root (`microservices-kafka-lab`).
2. Build and start services:

```bash
docker-compose up -d --build
```

3. Wait a few seconds for Kafka and services to initialize. Check service status:

```bash
docker-compose ps
```

4. Check Kafka topics (runs inside the `kafka` container):

```bash
docker exec kafka kafka-topics --bootstrap-server localhost:9092 --list
```

## Test the flow
- Using Postman: Import the collection and environment from `Kaffka-Example/postman/`, activate the environment `Kafka Microservices - Local Docker`, then run `Create Order` in the collection.

- Using curl (example):

```bash
curl -s -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{"orderId":"ORD-TEST-1","sku":"SKU-ABC","quantity":2}' -w "\nHTTP_STATUS:%{http_code}\n"
```

Expected: the gateway forwards to `order-service`, which publishes to `order-topic`. `inventory-service` and `billing-service` subscribe to `order-topic` and will log received events.

## Logs and verification
- Tail the gateway or service logs:

```bash
docker logs api-gateway --tail 200
docker logs order-service --tail 200
docker logs inventory-service --tail 200
docker logs billing-service --tail 200
```

- Optional: stream messages directly from the broker (console consumer):

```bash
docker exec kafka kafka-console-consumer --bootstrap-server localhost:9092 --topic order-topic --from-beginning --timeout-ms 20000
```

## Notes
- Kafka runs in KRaft mode (no Zookeeper). See `docker-compose.yml` for `KAFKA_PROCESS_ROLES` and `KAFKA_CLUSTER_ID` settings.
- This setup is for local development only — do not use these settings in production.

