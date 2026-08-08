# Milestones

This document tracks the development milestones of the `e-commerce-platform` project.

The project is intentionally milestone-based. Each milestone introduces a focused backend, integration, DevOps or cloud-native concept while keeping implemented functionality clearly separated from work in progress and planned extensions.

## Overview

| Milestone | Focus | Status |
| --- | --- | --- |
| 1 | Basic service structure | ![Done](https://img.shields.io/badge/Done-brightgreen) |
| 2 | Messaging with RabbitMQ | ![Done](https://img.shields.io/badge/Done-brightgreen) |
| 3 | External system integration | ![In Progress](https://img.shields.io/badge/In%20Progress-blue) |
| 4 | Analytics data in S3-compatible storage | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 5 | Spring Cloud API Gateway | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 6 | Container image publishing | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 7 | Optimized Java container builds | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 8 | Kubernetes | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 9 | Observability stack | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |

---

## Milestone 1: Basic Service Structure

![Done](https://img.shields.io/badge/Done-brightgreen)

### Goal

Establish the initial multi-service project structure and make the platform runnable in a local development environment.

### Implementation Scope

- create separate Spring Boot service modules
- define initial responsibilities for each service
- provide Dockerfiles for local container builds
- configure local orchestration with Docker Compose
- introduce RabbitMQ as the initial infrastructure component
- establish the foundation for later integration and platform milestones

### Implemented Components

```text
e-commerce-platform
├── order-service
├── inventory-service
├── notification-service
├── analytics-service
├── docs
└── compose.yaml
```

The local environment provides the application service structure and supporting infrastructure required for the following milestones.

### Outcome

A locally runnable multi-service Java backend foundation that can be extended incrementally without introducing all platform concerns at once.

### Status

![Done](https://img.shields.io/badge/Done-brightgreen)

---

## Milestone 2: Messaging with RabbitMQ

![Done](https://img.shields.io/badge/Done-brightgreen)

### Goal

Introduce asynchronous communication between backend components using RabbitMQ.

### Implementation Scope

- configure RabbitMQ exchanges, queues and bindings
- provide an order-related message producer in `order-service`
- consume order-related messages in `inventory-service`
- consume order-related messages in `notification-service`
- deserialize and process incoming messages
- reduce direct runtime coupling between backend components
- run the messaging infrastructure locally through Docker Compose

### Implemented Messaging Structure

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

The `order-service` contains the producer component required to publish order-related messages.

The `inventory-service` and `notification-service` contain RabbitMQ consumers.

The `analytics-service` is intentionally not part of the current implemented consumer flow. Analytics event processing is planned for a later milestone.

### Outcome

The project contains the core components required for asynchronous message-driven communication between the order-related producer and multiple independent consumers.

### Status

![Done](https://img.shields.io/badge/Done-brightgreen)

---

## Milestone 3: External System Integration

![In Progress](https://img.shields.io/badge/In%20Progress-blue)

### Goal

Integrate the platform with an external business system and establish a clear technical boundary between internal application logic and external-system communication.

Odoo 17 is used as the local external integration target.

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
  |
  v
PostgreSQL
```

### Implemented So Far

- add Odoo 17 to the local Docker Compose environment
- provide PostgreSQL for the local Odoo instance
- consume order-related messages in `inventory-service`
- delegate external-system communication to a dedicated `OdooService`
- establish communication with Odoo through a Java/XML-RPC client
- separate Odoo-specific integration concerns from RabbitMQ message consumption

### Current Work

- refine the external order integration flow
- map internal order-related data to appropriate Odoo business objects
- define the required external operations
- validate integration boundaries
- improve external communication error handling
- document technical decisions and trade-offs

### Remaining Work

- implement the intended end-to-end external order workflow
- validate business-level responses from Odoo
- improve handling of unavailable or failing external dependencies
- define retry or recovery behavior where appropriate
- add integration-focused tests
- document the completed integration flow

### Outcome

A documented and working integration path between the platform and Odoo with external-system-specific logic isolated behind a dedicated integration boundary.

### Status

![In Progress](https://img.shields.io/badge/In%20Progress-blue)

---

## Milestone 4: Analytics Data in S3-Compatible Storage

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Introduce analytics event processing and persist structured analytics data in S3-compatible object storage.

### Planned Scope

- implement analytics event consumption in `analytics-service`
- start an S3-compatible object storage service locally
- create an analytics bucket
- integrate an S3-compatible SDK
- transform analytics-relevant events into structured objects
- persist analytics data outside the application runtime
- define an object naming and organization strategy
- document storage behavior and design decisions

### Planned Flow

```text
RabbitMQ
  |
  v
analytics-service
  |
  v
structured analytics data
  |
  v
S3-compatible object storage
```

### Expected Outcome

The `analytics-service` can consume analytics-relevant events and persist structured data in S3-compatible object storage.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 5: Spring Cloud API Gateway

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Introduce a central API entry point for routing external requests to backend services.

### Planned Scope

- add a Spring Cloud Gateway service
- define routes for backend services
- route external requests through a central entry point
- reduce direct exposure of individual service ports
- prepare the architecture for later Kubernetes ingress concepts
- provide a location for future cross-cutting concerns

Possible route structure:

| Route | Target Service |
| --- | --- |
| `/orders/**` | `order-service` |
| `/inventory/**` | `inventory-service` |
| `/notifications/**` | `notification-service` |
| `/analytics/**` | `analytics-service` |

Possible later gateway concerns include:

- authentication
- authorization
- request logging
- rate limiting
- centralized error handling

### Expected Outcome

The platform exposes a central API entry point that routes requests to internal backend services.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 6: Container Image Publishing

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Publish versioned container images so that individual services can be reused and deployed without rebuilding them from local source code.

### Planned Scope

- build container images for application services
- define consistent image names
- define a versioning and tagging strategy
- publish images to a container registry
- document pull and run instructions
- prepare images for later deployment scenarios
- make deployment artifacts independent from local source builds

### Expected Outcome

Application services are available as versioned container images through a container registry.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 7: Optimized Java Container Builds

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Evaluate different approaches for building smaller and more efficient Java container images.

### Planned Scope

- review the existing Dockerfile-based approach
- evaluate multi-stage container builds
- evaluate Maven plugin-based image generation
- inspect resulting image sizes
- compare build times
- evaluate runtime image size and structure
- document trade-offs between transparency, simplicity and optimization
- select an appropriate strategy for the project

### Expected Outcome

A documented comparison of Java container build approaches and a justified build strategy for the platform.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 8: Kubernetes

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Move from Docker Compose-based local execution towards Kubernetes-oriented deployment.

### Planned Scope

- create Kubernetes manifests
- deploy application services to a local Kubernetes cluster
- configure Kubernetes Services
- introduce internal service discovery
- configure service-to-service connectivity
- deploy required infrastructure components
- integrate the planned API Gateway
- expose selected APIs through a centralized entry point
- document deployment and troubleshooting procedures

### Planned Direction

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
```

### Expected Outcome

The platform can be deployed and operated in a local Kubernetes-oriented environment instead of relying exclusively on Docker Compose.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 9: Observability Stack

![Planned](https://img.shields.io/badge/Planned-lightgrey)

### Goal

Introduce observability concepts for inspecting service health, runtime behavior, failures and performance.

### Planned Scope

- expose application metrics
- collect metrics with Prometheus
- visualize metrics with Grafana
- centralize logs with Loki
- introduce distributed tracing with Tempo
- configure basic alerting with Alertmanager
- create initial dashboards
- add relevant service health information
- document observability setup and usage

### Planned Stack

| Tool | Purpose |
| --- | --- |
| Prometheus | Metrics collection |
| Grafana | Dashboards and visualization |
| Loki | Log aggregation |
| Tempo | Distributed tracing |
| Alertmanager | Alerting |

### Expected Outcome

A basic observability environment that provides visibility into service behavior through metrics, logs, traces and alerts.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Long-Term Project Direction

The milestones progressively extend the project from a local multi-service backend into a broader platform architecture.

The long-term technical direction includes:

- Java / Spring Boot backend development
- REST-based application interfaces
- asynchronous communication
- external business system integration
- analytics event processing
- object storage
- API Gateway routing
- container image publishing
- optimized Java container builds
- Kubernetes-oriented deployment
- metrics, logging, tracing and alerting
- integration testing
- resilience and failure-handling concepts
- DevOps-oriented technical documentation

The roadmap intentionally introduces these concepts incrementally instead of presenting planned functionality as already implemented.

---

## Status Legend

| Status | Description |
| --- | --- |
| ![Planned](https://img.shields.io/badge/Planned-lightgrey) | Planned concept or upcoming implementation step |
| ![In Progress](https://img.shields.io/badge/In%20Progress-blue) | Currently being designed, implemented or documented |
| ![Done](https://img.shields.io/badge/Done-brightgreen) | Milestone scope implemented to the level defined for the project |
| ![Deferred](https://img.shields.io/badge/Deferred-orange) | Intentionally postponed because another milestone has priority |

---

## Related Documentation

| Document | Purpose |
| --- | --- |
| [README.md](../README.md) | Project overview, local setup and current project status |
| [ARCHITECTURE.md](./ARCHITECTURE.md) | Current architecture, planned target architecture and technical decisions |
