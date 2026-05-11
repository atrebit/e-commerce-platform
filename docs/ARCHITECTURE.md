# Architecture

This document describes the current and planned architecture of the `e-commerce-platform` project.

The project is a milestone-based Java backend portfolio project. It starts with a local multi-service setup and is extended step by step with messaging, external API integration, object storage, container image publishing, Kubernetes-oriented deployment and observability.

---

## Architectural Goals

The architecture is designed to demonstrate:

- Java backend development
- service-oriented architecture
- REST-based service interfaces
- asynchronous messaging with RabbitMQ
- external API integration
- Docker-based local development
- object storage concepts
- container image publishing
- optimized Java container builds
- Kubernetes-oriented deployment
- observability with metrics, logs, traces and alerting

The project intentionally grows through milestones. It does not start as a fully production-ready distributed system.

---

## Current Architecture

### Service Structure

```text
e-commerce-platform
├── order-service
├── inventory-service
├── notification-service
├── analytics-service
└── rabbitmq

```

### Current Components

| Component              | Type                     | Responsibility                                                 |
| ---------------------- | ------------------------ | -------------------------------------------------------------- |
| `order-service`        | Java backend service     | Handles order-related functionality and publishes order events |
| `inventory-service`    | Java backend service     | Handles product and stock-related functionality                |
| `notification-service` | Java backend service     | Handles notification-related functionality                     |
| `analytics-service`    | Java backend service     | Collects, processes or stores analytics-related data           |
| `rabbitmq`             | Infrastructure component | Message broker for asynchronous service communication          |
| Docker Compose         | Local runtime            | Starts the services and infrastructure locally                 |

### Current Runtime View

```text
Client
  |
  v
order-service
  |
  v
RabbitMQ
  |
  +--> inventory-service
  |
  +--> notification-service
  |
  +--> analytics-service
```

### Local Development

The platform is currently designed to run locally with Docker Compose.

```bash
docker compose up --build
```

| Component              | Purpose                      |
| ---------------------- | ---------------------------- |
| `order-service`        | Order service runtime        |
| `inventory-service`    | Inventory service runtime    |
| `notification-service` | Notification service runtime |
| `analytics-service`    | Analytics service runtime    |
| `rabbitmq`             | Local message broker         |

---

## Communication Model

The platform uses two communication styles.

| Communication Type     | Direction          | Purpose                                     |
| ---------------------- | ------------------ | ------------------------------------------- |
| Synchronous HTTP       | Client to service  | Direct request-response communication       |
| Asynchronous messaging | Service to service | Event-driven communication through RabbitMQ |

### Synchronous Communication

Synchronous HTTP communication is used when an immediate response is required.

Examples:

- creating an order
- querying inventory information
- retrieving service-specific data
- exposing REST-oriented endpoints for local testing

### Asynchronous Communication

RabbitMQ is used for asynchronous communication between backend services.

The goal is to reduce direct runtime coupling between services. Instead of calling all downstream services directly, the `order-service` publishes an event. Other services consume and process that event independently.

```text
order-service
  -> publishes order event
    -> inventory-service processes stock-related behavior
    -> notification-service processes notification-related behavior
    -> analytics-service records analytics-related data
```

Potential event types:

- order created events
- inventory update events
- notification events
- analytics events
- future workflow events

---

## Service Responsibilities

### order-service

The `order-service` is responsible for order-related functionality.

Responsibilities:

- expose order-related REST endpoints
- accept order creation requests
- validate basic order input
- process order-related data
- publish order-related events
- coordinate the first e-commerce workflow steps

Architectural role:

```text
order-service
  -> main entry point for order workflows
  -> producer of order-related events
```

### inventory-service

The `inventory-service` is responsible for product and stock-related functionality.

Responsibilities:

- expose inventory-related endpoints where useful
- manage product or stock-related data
- react to order-related events
- reserve or reduce stock where applicable
- provide inventory information to other parts of the platform

Architectural role:

```text
RabbitMQ
  -> inventory-service
    -> inventory-related processing
```

### notification-service

The `notification-service` is responsible for notification-related functionality.

Responsibilities:

- consume notification-relevant events
- send or simulate order notifications
- decouple notification behavior from order processing
- provide a basis for later notification channel extensions

Architectural role:

```text
RabbitMQ
  -> notification-service
    -> notification-related processing
```

### analytics-service

The `analytics-service` is responsible for analytics-related functionality.

Responsibilities:

- collect analytics-relevant events
- process order or platform events
- prepare analytics data for storage
- later write analytics data to S3-compatible object storage
- provide a basis for reporting, dashboards or exported analytics data

Architectural role:

```text
RabbitMQ
  -> analytics-service
    -> analytics data
      -> S3-compatible object storage
```

---

## Infrastructure

### RabbitMQ

RabbitMQ is the message broker for asynchronous service communication.

Responsibilities:

- receive events from producing services
- distribute events to consuming services
- decouple producers from consumers
- support event-driven workflows
- enable asynchronous processing

Current role:

```text
order-service
  -> RabbitMQ
    -> inventory-service
    -> notification-service
    -> analytics-service
```

### Docker Compose

Docker Compose is used for local development.

Responsibilities:

- start all backend services locally
- start RabbitMQ locally
- expose service ports
- provide reproducible local execution
- simplify development and testing

---

## Planned Target Architecture

The planned architecture extends the current setup with API Gateway routing, external integration, object storage, containerized delivery, Kubernetes deployment and observability.

```text
Client / API User
  |
  v
API Gateway
  |
  +--> order-service
  |
  +--> inventory-service
  |
  +--> notification-service
  |
  +--> analytics-service

order-service
  |
  +--> RabbitMQ
  |
  +--> External Odoo System

RabbitMQ
  |
  +--> inventory-service
  |
  +--> notification-service
  |
  +--> analytics-service

analytics-service
  |
  +--> S3-Compatible Object Storage

All services
  |
  +--> Metrics
  +--> Logs
  +--> Traces
```

### Planned Components

| Component                    | Purpose                                 | Status      |
| ---------------------------- | --------------------------------------- | ----------- |
| Odoo API / SDK               | External business system integration    | In progress |
| S3-compatible object storage | Analytics data storage                  | Planned     |
| API Gateway                  | Central entry point and request routing | Planned     |
| Container registry           | Versioned service image publishing      | Planned     |
| Kubernetes                   | Cluster-oriented deployment             | Planned     |
| Prometheus                   | Metrics collection                      | Planned     |
| Grafana                      | Dashboards and visualization            | Planned     |
| Loki                         | Log aggregation                         | Planned     |
| Tempo                        | Distributed tracing                     | Planned     |
| Alertmanager                 | Alerting                                | Planned     |

---

## External API Integration

The project uses Odoo as an example external integration target.

The integration is intended to demonstrate how a backend service can communicate with an external business system.

Planned integration topics:

- direct HTTP requests against an external API
- SDK-based access where suitable
- comparison between direct API calls and SDK usage
- documentation of trade-offs and implementation decisions
- isolation of external integration logic

Planned position in the architecture:

```text
order-service
  -> Odoo API / SDK
```

The initial goal is to understand API communication directly before relying too heavily on framework-specific abstractions.

---

## Object Storage

The `analytics-service` is planned to store analytics data in S3-compatible object storage.

Planned storage flow:

```text
RabbitMQ
  -> analytics-service
    -> creates analytics object
      -> writes object to S3-compatible bucket
```

Possible use cases:

- storing order events
- storing exported analytics data
- storing generated reports
- persisting event data independently from application runtime

Planned implementation steps:

- start an S3-compatible server locally
- create an analytics bucket
- integrate an S3-compatible SDK
- write structured analytics data into the bucket
- document object naming and storage conventions

Example object naming strategy:

```text
analytics/
  orders/
    year=2026/
      month=05/
        day=11/
          order-event-<event-id>.json
```

---

## API Gateway Direction

The API Gateway is planned as the central entry point for external requests.

Planned responsibilities:

- provide one external access point
- route requests to internal backend services
- hide individual service ports from external clients
- prepare the project for Kubernetes ingress concepts
- provide a place for cross-cutting concerns

Possible route structure:

| Route               | Target Service         | Purpose                       |
| ------------------- | ---------------------- | ----------------------------- |
| `/orders/**`        | `order-service`        | Order-related requests        |
| `/inventory/**`     | `inventory-service`    | Inventory-related requests    |
| `/notifications/**` | `notification-service` | Notification-related requests |
| `/analytics/**`     | `analytics-service`    | Analytics-related requests    |

Possible future gateway concerns:

- request routing
- request logging
- authentication concepts
- authorization concepts
- rate limiting
- central error handling

---

## Container Image Strategy

Each service is intended to be containerized independently.

The long-term goal is to publish versioned container images so that services can be downloaded and deployed without rebuilding them locally.

Planned image strategy:

- build one image per service
- use clear image names
- use versioned tags
- publish images to a container registry
- make deployment independent from local source builds

Example target image names:

```text
registry.example.com/e-commerce-platform/order-service:1.0.0
registry.example.com/e-commerce-platform/inventory-service:1.0.0
registry.example.com/e-commerce-platform/notification-service:1.0.0
registry.example.com/e-commerce-platform/analytics-service:1.0.0
registry.example.com/e-commerce-platform/api-gateway:1.0.0
```

### Java Container Build Optimization

The project is also used to evaluate different ways of building Java container images.

| Area                      | Purpose                                         |
| ------------------------- | ----------------------------------------------- |
| Classic Dockerfile builds | Understand a transparent manual image build     |
| Multi-stage builds        | Separate build and runtime layers               |
| Maven plugin-based builds | Evaluate build automation through Maven tooling |
| Optimized runtime images  | Reduce final image size                         |
| Image size comparison     | Compare output of different strategies          |
| Build time comparison     | Evaluate development and CI/CD impact           |

The goal is to understand how Java services can be packaged efficiently for container-based deployment.

---

## Kubernetes Direction

The project is planned to move from Docker Compose towards Kubernetes-oriented deployment concepts.

Planned Kubernetes topics:

- create Kubernetes manifests
- deploy services to a local cluster
- configure service discovery
- connect services through Kubernetes networking
- introduce an API Gateway
- expose selected APIs through a centralized entry point
- integrate observability components into the Kubernetes environment

Possible target structure:

```text
Kubernetes Cluster
  |
  +--> api-gateway
  |
  +--> order-service
  |
  +--> inventory-service
  |
  +--> notification-service
  |
  +--> analytics-service
  |
  +--> rabbitmq
  |
  +--> observability components
```

---

## Observability Target Architecture

The project is planned to move gradually towards production-like observability.

Planned observability stack:

| Tool         | Purpose                      |
| ------------ | ---------------------------- |
| Prometheus   | Metrics collection           |
| Grafana      | Dashboards and visualization |
| Loki         | Log aggregation              |
| Tempo        | Distributed tracing          |
| Alertmanager | Alerting                     |

Target observability capabilities:

- service health checks
- application metrics
- centralized logs
- distributed traces between services
- dashboards for service behavior
- alerting for failures or abnormal states

Possible target flow:

```text
services
  -> expose metrics
    -> Prometheus
      -> Grafana

services
  -> write logs
    -> Loki
      -> Grafana

services
  -> emit traces
    -> Tempo
      -> Grafana

Prometheus
  -> evaluates alert rules
    -> Alertmanager
```

---

## Architectural Decisions

| Decision                 | Reason                                     | Trade-Off                                            |
| ------------------------ | ------------------------------------------ | ---------------------------------------------------- |
| Use multiple services    | Demonstrates service-oriented architecture | More local setup complexity                          |
| Use RabbitMQ             | Reduces direct coupling between services   | Requires message contracts and broker infrastructure |
| Use Docker Compose first | Keeps the project locally runnable         | Not a production-grade orchestration solution        |
| Add Kubernetes later     | Avoids early overengineering               | Cluster deployment is not available from the start   |
| Add observability later  | Requires meaningful runtime behavior first | Early debugging depends on local logs                |

---

## Architectural Principles

The project follows these principles:

- keep service responsibilities clear
- prefer incremental implementation over over-engineering
- document technical decisions explicitly
- use Docker Compose for local reproducibility
- introduce asynchronous communication where it reduces coupling
- keep external integration logic isolated
- treat observability as part of the platform architecture
- prepare services for container-based deployment
- evolve towards Kubernetes after the local architecture is understandable

---

## Current Limitations

Current limitations:

- no central API Gateway yet
- no Kubernetes deployment yet
- no published container images yet
- no optimized Java image strategy yet
- no S3-compatible analytics storage yet
- no full observability stack yet
- external API integration is still in progress
- persistence concepts are intentionally limited
- retry and dead-letter queue behavior is not yet fully defined
- security concepts are not yet the main focus

---

## Future Extension Points

Possible future extensions:

- persistent databases per service
- authentication and authorization
- API Gateway filters
- centralized configuration
- schema validation for events
- dead-letter queues
- retry mechanisms
- idempotent event consumers
- integration tests
- contract tests
- CI/CD pipeline automation
- structured logging
- distributed tracing
- alert rules
- resilience patterns
- service health checks

---

## Architecture Roadmap

| Step | Focus                    | Result                                            |
| ---- | ------------------------ | ------------------------------------------------- |
| 1    | Basic service structure  | Services exist as separate components             |
| 2    | RabbitMQ messaging       | Services communicate asynchronously               |
| 3    | External API integration | Platform communicates with Odoo                   |
| 4    | S3-compatible storage    | Analytics data is stored externally               |
| 5    | API Gateway              | Central entry point is introduced                 |
| 6    | Image publishing         | Services are available as container images        |
| 7    | Optimized builds         | Java images are compared and improved             |
| 8    | Kubernetes               | Services are deployed in a cluster-oriented setup |
| 9    | Observability            | Metrics, logs, traces and alerts are available    |

---

## Related Documentation

| Document                         | Purpose                                              |
| -------------------------------- | ---------------------------------------------------- |
| [README.md](./README.md)         | Project overview, quick start and repository purpose |
| [MILESTONES.md](./MILESTONES.md) | Implementation roadmap and milestone tracking        |
