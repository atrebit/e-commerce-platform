# E-Commerce Platform

![In Progress](https://img.shields.io/badge/status-in%20progress-blue)

Milestone-based **Java / Spring Boot backend portfolio project** for building and progressively extending a distributed e-commerce platform.

The project combines backend development, REST APIs, asynchronous messaging, external system integration and Docker-based local development. Additional DevOps and cloud-native concepts are introduced incrementally through clearly documented milestones.

## Project Goal

The goal of this project is to build a realistic multi-service backend platform and document its technical evolution step by step.

The project focuses on:

- Java and Spring Boot backend development
- REST-oriented service interfaces
- asynchronous communication with RabbitMQ
- external API and system integration
- Docker-based local development
- modular service architecture
- object storage for analytics data
- container image publishing and optimization
- observability
- Kubernetes and API Gateway concepts

Planned technologies are introduced milestone by milestone rather than presented as already implemented functionality.

## Current Status

The core multi-service structure and RabbitMQ-based messaging are implemented.

The current development focus is **Milestone 3: External API Integration**.

The local Docker Compose environment already includes **Odoo 17** and its PostgreSQL database as the external system environment for this milestone. Application-side integration and the evaluation of suitable integration approaches are currently in progress.

Current milestone status:

| Milestone | Focus | Status |
| --- | --- | --- |
| 1 | Basic service structure | ![Done](https://img.shields.io/badge/Done-brightgreen) |
| 2 | Messaging with RabbitMQ | ![Done](https://img.shields.io/badge/Done-brightgreen) |
| 3 | External API integration | ![In Progress](https://img.shields.io/badge/In%20Progress-blue) |
| 4 | Analytics data in S3-compatible storage | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 5 | Spring Cloud API Gateway | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 6 | Container image publishing | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 7 | Optimized Java container builds | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 8 | Kubernetes | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |
| 9 | Observability stack | ![Planned](https://img.shields.io/badge/Planned-lightgrey) |

For detailed milestone scopes and progress, see [MILESTONES.md](./docs/MILESTONES.md).

## Architecture Overview

The repository is organized as a multi-service Java backend platform.

```text
e-commerce-platform
├── order-service
├── inventory-service
├── notification-service
├── analytics-service
├── docs
└── compose.yaml
```

The application services are developed as separate components and orchestrated locally through Docker Compose.

### Application Services

| Service | Purpose |
| --- | --- |
| `order-service` | Handles order-related workflows and publishes order events |
| `inventory-service` | Handles product and stock-related functionality |
| `notification-service` | Processes notification-related events |
| `analytics-service` | Provides the basis for analytics-related processing |

### Supporting Infrastructure

| Component | Purpose |
| --- | --- |
| RabbitMQ | Message broker for asynchronous service communication |
| Odoo 17 | Local external system used for the current integration milestone |
| PostgreSQL | Database backing the local Odoo environment |
| Docker Compose | Local orchestration of services and infrastructure |

### Current Communication Model

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
```

RabbitMQ is used to decouple order-related processing from downstream services.

The current milestone extends the platform with communication to an external system while keeping integration concerns separated from the core service responsibilities.

## Tech Stack

### Currently Used

| Area | Technologies |
| --- | --- |
| Backend | Java 21, Spring Boot, Maven |
| APIs | REST-oriented HTTP interfaces |
| Messaging | RabbitMQ, Spring AMQP |
| Local development | Docker, Docker Compose |
| External integration environment | Odoo 17, PostgreSQL |
| Build tooling | Maven |

### Planned

| Area | Technologies / Concepts |
| --- | --- |
| Object storage | S3-compatible storage and SDK integration |
| API routing | Spring Cloud API Gateway |
| Image distribution | Container registry and versioned images |
| Container optimization | Alternative Java image build strategies |
| Deployment | Kubernetes |
| Observability | Prometheus, Grafana, Loki, Tempo, Alertmanager |

## Planned Platform Direction

The platform is intentionally developed through incremental technical milestones.

The planned progression includes:

1. basic multi-service backend structure
2. RabbitMQ-based asynchronous messaging
3. external API integration
4. S3-compatible analytics storage
5. Spring Cloud API Gateway
6. container image publishing
7. optimized Java container builds
8. Kubernetes deployment
9. observability with metrics, logs, traces and alerting

This structure keeps implemented functionality clearly separated from future technical goals while allowing the architecture to evolve step by step.

## Local Development

Clone the repository:

```bash
git clone https://github.com/atrebit/e-commerce-platform.git
cd e-commerce-platform
```

Start the local environment:

```bash
docker compose up --build
```

### Local Ports

| Component | Port |
| --- | ---: |
| `order-service` | `8081` |
| `inventory-service` | `8082` |
| `notification-service` | `8083` |
| `analytics-service` | `8084` |
| Odoo | `8069` |
| RabbitMQ | `5672` |
| RabbitMQ Management UI | `15672` |

RabbitMQ Management UI:

```text
http://localhost:15672
```

Odoo:

```text
http://localhost:8069
```

## Documentation

| Document | Purpose |
| --- | --- |
| [ARCHITECTURE.md](./docs/ARCHITECTURE.md) | Describes the architecture, service responsibilities and technical direction |
| [MILESTONES.md](./docs/MILESTONES.md) | Tracks implementation milestones, scope and project progress |

## Repository Purpose

This repository is a portfolio and learning project used to demonstrate the progressive development of a backend platform.

The milestone-based approach is intended to make it transparent:

- what is already implemented
- what is currently being developed
- which technical concepts are planned next
- how architectural decisions evolve as the platform grows

## Long-Term Learning Outcome

The intended outcome is practical experience across:

- Java / Spring Boot backend development
- REST API design
- service-oriented architecture
- asynchronous communication
- external system integration
- containerization
- object storage
- container image publishing and optimization
- Kubernetes-oriented deployment
- observability
- technical and architectural documentation
