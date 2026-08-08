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
- external system integration
- Docker-based local development
- object storage concepts
- container image publishing
- optimized Java container builds
- Kubernetes-oriented deployment
- observability with metrics, logs, traces and alerting

The project intentionally grows through milestones. It does not start as a fully production-ready distributed system.

---

## Current Architecture

### Repository Structure

```text
e-commerce-platform
├── order-service
├── inventory-service
├── notification-service
├── analytics-service
├── docs
└── compose.yaml
```

The Java services are maintained as separate application components.

Supporting infrastructure and the local external integration environment are orchestrated through Docker Compose.

### Current Components

| Component | Type | Current Role |
| --- | --- | --- |
| `order-service` | Java / Spring Boot service | Contains RabbitMQ configuration and a producer for order-related messages |
| `inventory-service` | Java / Spring Boot service | Consumes order-related messages and contains the current Odoo integration work |
| `notification-service` | Java / Spring Boot service | Consumes order-related messages and simulates notification processing |
| `analytics-service` | Java / Spring Boot service | Application scaffold for future analytics functionality |
| RabbitMQ | Infrastructure | Message broker for asynchronous communication |
| Odoo 17 | External system | Local target system for the current external integration milestone |
| PostgreSQL | Infrastructure | Database backing the local Odoo instance |
| Docker Compose | Local orchestration | Starts application services and supporting infrastructure |

### Implemented Messaging and Integration Components

```text
order-service
  MessageProducer
       |
       v
   RabbitMQ
       |
       +----------------------+
       |                      |
       v                      v
inventory-service     notification-service
       |
       v
   OdooService
       |
       v
     Odoo 17
       |
       v
   PostgreSQL
```

The `order-service` contains the RabbitMQ producer component for order-related messages.

A complete REST-based order entry workflow that invokes the producer is not yet implemented.

The `inventory-service` and `notification-service` contain RabbitMQ consumers.

The inventory consumer currently delegates received order data to the Odoo integration service.

The `analytics-service` is part of the repository and local Compose setup, but analytics event processing is not yet implemented.

The external Odoo integration is currently under development as part of Milestone 3.

### Local Development

The local environment is orchestrated with Docker Compose:

```bash
docker compose up --build
```

The current Compose setup includes:

| Component | Purpose |
| --- | --- |
| `order-service` | Order-related backend service |
| `inventory-service` | Inventory service and current external-integration component |
| `notification-service` | Notification-related backend service |
| `analytics-service` | Analytics service scaffold |
| RabbitMQ | Local message broker |
| Odoo 17 | Local external integration target |
| PostgreSQL | Database used by Odoo |

---

## Communication Model

The architecture distinguishes between synchronous request-response communication and asynchronous service communication.

### Planned Synchronous Communication

REST-based request-response communication is intended for operations that require an immediate response.

Planned use cases include:

- creating an order
- querying inventory information
- retrieving service-specific data
- exposing REST endpoints for local testing

A complete REST-based order workflow is not yet implemented.

### Current Asynchronous Communication

RabbitMQ is used for asynchronous communication between backend components.

The `order-service` contains a producer for order-related messages.

Currently implemented consumers exist in the `inventory-service` and `notification-service`.

```text
order-service
  MessageProducer
       |
       v
    RabbitMQ
       |
       +--> inventory-service
       |
       +--> notification-service
```

The `analytics-service` is planned to participate in the event flow in a later milestone but does not currently process RabbitMQ events.

Potential future event types include:

- inventory update events
- notification events
- analytics events
- additional workflow events

---

## Service Responsibilities

### order-service

The `order-service` currently contains the RabbitMQ configuration and message producer for order-related events.

#### Current Implementation

- configure RabbitMQ messaging
- provide a producer for order-related messages
- publish messages to the configured exchange when the producer is invoked

#### Planned Responsibilities

- expose order-related REST endpoints
- accept order creation requests
- validate order input
- coordinate order-related workflow steps
- publish order events as part of the order workflow

Planned architectural direction:

```text
Client
  |
  v
REST order workflow
  |
  v
order-service
  |
  v
RabbitMQ
  |
  v
downstream consumers
```

The REST-based order workflow is not yet implemented.

---

### inventory-service

The `inventory-service` currently consumes order-related RabbitMQ messages and contains the active Odoo integration work.

#### Current Implementation

- consume order-related messages from RabbitMQ
- deserialize incoming order data
- delegate external-system communication to `OdooService`
- provide the current integration boundary to Odoo

#### Planned Responsibilities

- manage inventory-related data
- implement stock-related behavior
- reserve or reduce stock where applicable
- expose inventory information where required

Current architectural role:

```text
RabbitMQ
  |
  v
inventory-service
  |
  v
OdooService
  |
  v
Odoo 17
```

---

### notification-service

The `notification-service` currently consumes order-related RabbitMQ messages and simulates notification processing.

#### Current Implementation

- consume order-related messages
- process incoming message content
- log simulated notification output for local demonstration

#### Planned Responsibilities

- expand notification processing
- support additional notification behavior or channels
- remain decoupled from the order workflow through messaging

Current architectural role:

```text
RabbitMQ
  |
  v
notification-service
  |
  v
simulated notification processing
```

---

### analytics-service

The `analytics-service` currently provides the application scaffold for future analytics functionality.

#### Planned Responsibilities

- consume analytics-relevant events
- process order or platform events
- prepare structured analytics data
- persist analytics data in S3-compatible object storage
- provide a basis for reporting, dashboards or exported analytics data

Planned architectural role:

```text
RabbitMQ
  |
  v
analytics-service
  |
  v
analytics data
  |
  v
S3-compatible object storage
```

Analytics event processing and object storage integration are not yet implemented.

---

## Infrastructure

### RabbitMQ

RabbitMQ is the message broker for asynchronous service communication.

Its architectural responsibilities include:

- receive messages from producing components
- distribute messages to consuming services
- reduce direct runtime coupling between services
- support event-driven workflows
- enable asynchronous processing

Current flow:

```text
order-service
  MessageProducer
       |
       v
    RabbitMQ
       |
       +--> inventory-service
       |
       +--> notification-service
```

The `analytics-service` is planned as an additional consumer in a later milestone.

---

### Docker Compose

Docker Compose is used for local development and orchestration.

Current responsibilities:

- start the backend services locally
- start RabbitMQ
- start the local Odoo integration environment
- start the PostgreSQL database used by Odoo
- expose required local ports
- provide reproducible local execution
- simplify development and integration work

Docker Compose is intentionally used before introducing Kubernetes-oriented deployment.

---

### Odoo 17

Odoo 17 serves as the local external business system for the current integration milestone.

It provides a realistic target for experimenting with:

- external system communication
- integration boundaries
- external data models
- failure handling
- technology-specific client access
- translation between internal and external representations

The Odoo integration is currently incomplete and is not intended to represent a production-ready ERP integration.

---

### PostgreSQL

PostgreSQL currently provides the database for the local Odoo environment.

The application services do not yet implement independent persistent databases as part of the current architecture.

Service-specific persistence is a possible future extension.

---

## External API Integration

Odoo 17 is used as the local external system for the current integration milestone.

The current implementation places the external integration work within the `inventory-service`.

### Current Integration Direction

```text
RabbitMQ
  |
  v
inventory-service
  |
  v
OdooService
  |
  v
Odoo 17
```

The inventory consumer receives order-related data and delegates external-system communication to a dedicated `OdooService`.

The current integration establishes the technical integration structure but does not yet implement a complete external order synchronization workflow.

### Current Integration Topics

- consuming order-related messages
- mapping internal data for external-system communication
- isolating Odoo-specific integration logic
- validating integration boundaries
- handling external communication failures
- evaluating integration approaches and trade-offs

### Current Limitations

The Odoo integration is still under development.

The current implementation establishes communication with the external system and provides the integration layer required for further development.

Further work is required to:

- map incoming order data to suitable Odoo business objects
- implement the intended external order workflow
- validate business-level responses
- improve failure handling
- define retry or recovery behavior
- add integration tests

---

## Planned Target Architecture

The planned architecture extends the current setup with:

- complete REST-based workflows
- asynchronous messaging
- external system integration
- analytics event processing
- object storage
- centralized API routing
- versioned container images
- Kubernetes-oriented deployment
- observability

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
        v
     RabbitMQ
        |
        +--> inventory-service
        |
        +--> notification-service
        |
        +--> analytics-service


inventory-service
        |
        v
External Odoo System


analytics-service
        |
        v
S3-Compatible Object Storage


All services
        |
        +--> Metrics
        |
        +--> Logs
        |
        +--> Traces
```

### Planned Components

| Component | Purpose | Status |
| --- | --- | --- |
| Odoo integration | External business system integration | In progress |
| S3-compatible object storage | Analytics data storage | Planned |
| API Gateway | Central entry point and request routing | Planned |
| Container registry | Versioned service image publishing | Planned |
| Kubernetes | Cluster-oriented deployment | Planned |
| Prometheus | Metrics collection | Planned |
| Grafana | Dashboards and visualization | Planned |
| Loki | Log aggregation | Planned |
| Tempo | Distributed tracing | Planned |
| Alertmanager | Alerting | Planned |

---

## Object Storage

The `analytics-service` is planned to store analytics data in S3-compatible object storage.

### Planned Storage Flow

```text
RabbitMQ
  |
  v
analytics-service
  |
  v
structured analytics object
  |
  v
S3-compatible bucket
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

Object storage is not yet implemented.

---

## API Gateway Direction

The API Gateway is planned as the central entry point for external requests.

### Planned Responsibilities

- provide one external access point
- route requests to internal backend services
- hide individual service ports from external clients
- prepare the architecture for Kubernetes ingress concepts
- provide a location for cross-cutting concerns

Possible route structure:

| Route | Target Service | Purpose |
| --- | --- | --- |
| `/orders/**` | `order-service` | Order-related requests |
| `/inventory/**` | `inventory-service` | Inventory-related requests |
| `/notifications/**` | `notification-service` | Notification-related requests |
| `/analytics/**` | `analytics-service` | Analytics-related requests |

Possible future gateway concerns:

- request routing
- request logging
- authentication
- authorization
- rate limiting
- centralized error handling

The API Gateway is not yet implemented.

---

## Container Image Strategy

Each service is intended to be containerized independently.

The long-term goal is to publish versioned container images so that individual services can be deployed without rebuilding them from source.

### Planned Image Strategy

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

Container image publishing is not yet implemented.

### Java Container Build Optimization

The project is also intended to evaluate different approaches for building Java container images.

| Area | Purpose |
| --- | --- |
| Classic Dockerfile builds | Understand transparent manual image builds |
| Multi-stage builds | Separate build and runtime layers |
| Maven plugin-based builds | Evaluate build automation through Maven tooling |
| Optimized runtime images | Reduce final image size |
| Image size comparison | Compare different build strategies |
| Build time comparison | Evaluate development and CI/CD impact |

The goal is to understand how Java services can be packaged efficiently for container-based deployment.

---

## Kubernetes Direction

The project is planned to move from Docker Compose towards Kubernetes-oriented deployment concepts.

### Planned Kubernetes Topics

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

Kubernetes deployment is not yet implemented.

---

## Observability Target Architecture

The project is planned to move gradually towards production-like observability.

### Planned Observability Stack

| Tool | Purpose |
| --- | --- |
| Prometheus | Metrics collection |
| Grafana | Dashboards and visualization |
| Loki | Log aggregation |
| Tempo | Distributed tracing |
| Alertmanager | Alerting |

### Target Observability Capabilities

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

The observability stack is not yet implemented.

---

## Architectural Decisions

| Decision | Reason | Trade-Off |
| --- | --- | --- |
| Use multiple services | Demonstrates service-oriented architecture | Adds local setup and communication complexity |
| Use RabbitMQ | Enables asynchronous and decoupled communication | Requires message contracts and broker infrastructure |
| Use Docker Compose first | Keeps the project locally reproducible | Does not provide production-grade orchestration |
| Integrate Odoo incrementally | Provides a realistic external-system integration scenario | Introduces an additional external dependency |
| Keep external integration isolated | Reduces coupling to external-system-specific logic | Requires explicit translation between boundaries |
| Add persistence incrementally | Keeps early milestones focused | Services do not yet model full persistent business state |
| Add Kubernetes later | Avoids early overengineering | Cluster deployment is not available from the start |
| Add observability later | Requires meaningful runtime behavior first | Early debugging depends primarily on local logs |

---

## Architectural Principles

The project follows these principles:

- distinguish implemented functionality from planned functionality
- keep service responsibilities understandable
- prefer incremental implementation over early over-engineering
- document technical decisions explicitly
- use Docker Compose for local reproducibility
- introduce asynchronous communication where it reduces coupling
- keep external integration concerns isolated
- make integration boundaries explicit
- treat failure handling as part of integration design
- introduce observability as the platform becomes more operationally meaningful
- prepare services for container-based deployment
- evolve towards Kubernetes after the local architecture is understandable

---

## Current Limitations

Current limitations include:

- no complete REST-based order entry workflow yet
- no central API Gateway
- no analytics event processing
- no S3-compatible analytics storage
- external Odoo integration is still in progress
- no complete external order synchronization workflow
- persistence concepts are intentionally limited
- no Kubernetes deployment
- no published container images
- no optimized Java image strategy
- no full observability stack
- retry behavior is not yet fully defined
- dead-letter queue behavior is not yet fully defined
- integration test coverage is still limited
- security concepts are not yet the main focus

These limitations are intentional consequences of the milestone-based development approach rather than claims of completed platform functionality.

---

## Future Extension Points

Possible future extensions include:

- complete REST-based order workflows
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

| Step | Focus | Target Result |
| --- | --- | --- |
| 1 | Basic service structure | Services exist as separate components |
| 2 | RabbitMQ messaging | Asynchronous messaging components are established |
| 3 | External API integration | Platform integrates with Odoo |
| 4 | S3-compatible storage | Analytics data can be stored externally |
| 5 | API Gateway | Central entry point is introduced |
| 6 | Image publishing | Services are available as versioned container images |
| 7 | Optimized builds | Java container build strategies are compared and improved |
| 8 | Kubernetes | Services are deployed in a cluster-oriented setup |
| 9 | Observability | Metrics, logs, traces and alerts are available |

---

## Related Documentation

| Document | Purpose |
| --- | --- |
| [README.md](../README.md) | Project overview, local setup and current milestone status |
| [MILESTONES.md](./MILESTONES.md) | Implementation roadmap and milestone tracking |
