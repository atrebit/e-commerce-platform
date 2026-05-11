# E-Commerce Platform

Milestone-based Java backend portfolio project for building a distributed e-commerce platform with service-oriented architecture, messaging, Docker, external API integration, object storage, observability and Kubernetes-oriented deployment concepts.

The project is intentionally designed as a growing architecture playground. It starts with basic e-commerce functionality and gradually introduces more advanced backend, DevOps and cloud-native concepts.

## Project Goal

The goal of this project is to build a realistic multi-service backend platform and document its technical evolution step by step.

The project focuses on:

- Java backend development
- REST-oriented service interfaces
- asynchronous messaging
- external API integration
- Docker-based local development
- object storage for analytics data
- container image publishing
- optimized Java container builds
- observability with metrics, logs, traces and alerting
- Kubernetes and API Gateway concepts

## Current Status

![In Progress](https://img.shields.io/badge/status-in%20progress-blue)

Early-stage portfolio project.

The current focus is on establishing the basic service structure, local Docker-based development and the technical roadmap for further backend, integration and DevOps features.

## Architecture Overview

The repository is structured as a multi-service platform.

```text
e-commerce-platform
├── order-service
├── inventory-service
├── notification-service
├── analytics-service
└── rabbitmq
```

## Services

| Service                | Purpose                                               |
| ---------------------- | ----------------------------------------------------- |
| `order-service`        | Handles order-related functionality                   |
| `inventory-service`    | Handles product and stock-related functionality       |
| `notification-service` | Handles notification-related functionality            |
| `analytics-service`    | Collects or stores analytics-related data             |
| `rabbitmq`             | Message broker for asynchronous service communication |

The services are intended to be developed and extended independently while still being runnable together through Docker Compose.

## Planned Platform Direction

The project is developed incrementally through technical milestones.

Planned focus areas include:

- basic e-commerce workflows
- RabbitMQ-based messaging
- Odoo API or SDK integration
- S3-compatible object storage for analytics data
- published container images
- optimized Java image builds
- observability with Prometheus, Grafana, Loki, Tempo and Alertmanager
- Kubernetes-based deployment
- API Gateway integration

For the complete roadmap, see [MILESTONES.md](./docs/MILESTONES.md).

## Tech Stack

Current and planned technologies:

| Area                 | Technologies                                   |
| -------------------- | ---------------------------------------------- |
| Backend              | Java                                           |
| Local development    | Docker, Docker Compose                         |
| Messaging            | RabbitMQ                                       |
| External integration | Odoo API / SDK                                 |
| Object storage       | S3-compatible storage, S3 SDK                  |
| Build tooling        | Maven                                          |
| Observability        | Prometheus, Grafana, Loki, Tempo, Alertmanager |
| Deployment direction | Container registry, Kubernetes, API Gateway    |

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

## Local Ports

| Component              |    Port |
| ---------------------- | ------: |
| `order-service`        |  `8081` |
| `inventory-service`    |  `8082` |
| `notification-service` |  `8083` |
| `analytics-service`    |  `8084` |
| RabbitMQ Management UI | `15672` |

RabbitMQ Management UI:

```text
http://localhost:15672
```

## Documentation

| Document                                  | Purpose                                                                              |
| ----------------------------------------- | ------------------------------------------------------------------------------------ |
| [ARCHITECTURE.md](./docs/ARCHITECTURE.md) | Describes the current architecture, service responsibilities and technical direction |
| [MILESTONES.md](./docs/MILESTONES.md)     | Tracks the planned implementation milestones and project progress                    |

## Repository Purpose

This repository is a portfolio project.

It is used to demonstrate the step-by-step development of a realistic backend platform. The project is milestone-based so that external viewers can clearly see:

- what the project is intended to become
- which technical concepts are being practiced
- what has already been prepared
- which implementation steps are planned next

## Long-Term Learning Outcome

The intended outcome is a portfolio project that demonstrates practical knowledge in:

- backend service development
- service-oriented architecture
- asynchronous communication
- external API integration
- containerization
- object storage
- image publishing
- Java container optimization
- observability
- Kubernetes-oriented deployment
- DevOps-oriented documentation
