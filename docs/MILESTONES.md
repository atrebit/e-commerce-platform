# Milestones

This document summarizes the planned development milestones for the `e-commerce-platform` project.

The project is intentionally milestone-based. Each milestone introduces one or more backend, integration, DevOps or cloud-native concepts and documents the technical progress of the platform.

## Overview

| Milestone | Focus                                   | Status                                                          |
| --------- | --------------------------------------- | --------------------------------------------------------------- |
| 1         | Basic service structure                 | ![Done](https://img.shields.io/badge/Done-brightgreen)          |
| 2         | Messaging with RabbitMQ                 | ![Done](https://img.shields.io/badge/Done-brightgreen)          |
| 3         | External API integration                | ![In Progress](https://img.shields.io/badge/In%20Progress-blue) |
| 4         | Analytics data in S3-compatible storage | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |
| 5         | Spring Cloud API Gateway                | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |
| 6         | Container image publishing              | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |
| 7         | Optimized Java container builds         | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |
| 8         | Kubernetes                              | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |
| 9         | Observability stack                     | ![Planned](https://img.shields.io/badge/Planned-lightgrey)      |

---

## Milestone 1: Basic Service Structure

### Goal

Establish the initial multi-service project structure and make the platform runnable in a local development environment.

### Scope

- create separate service folders
- define basic responsibilities per service
- provide initial Dockerfiles for services
- configure local startup with Docker Compose
- include RabbitMQ as the initial infrastructure component

### Expected Outcome

A locally runnable multi-service setup that provides the foundation for further backend and DevOps development.

### Status

![Done](https://img.shields.io/badge/Done-brightgreen)

---

## Milestone 2: Messaging with RabbitMQ

### Goal

Introduce asynchronous communication between services using RabbitMQ.

### Scope

- publish order-related events from `order-service`
- consume events in `inventory-service`
- consume events in `notification-service`
- optionally consume events in `analytics-service`
- reduce direct synchronous dependencies between services
- test event-driven communication locally

### Example Flow

```text
order-service
  -> publishes order event
    -> inventory-service updates stock
    -> notification-service sends notification
    -> analytics-service records analytics data
```

### Expected Outcome

A decoupled service interaction model based on message-driven communication.

### Status

![Done](https://img.shields.io/badge/Done-brightgreen)

---

## Milestone 3: External API Integration

### Goal

Integrate an external system through API calls and compare different integration approaches.

### Scope

- evaluate Odoo API access
- implement direct HTTP requests for API calls
- evaluate SDK-based access where suitable
- compare direct HTTP integration with SDK-based integration
- document technical trade-offs
- avoid relying only on Spring-specific abstractions

### Expected Outcome

A documented external API integration approach with clear reasoning behind the selected implementation style.

### Status

![In Progress](https://img.shields.io/badge/In%20Progress-blue)

---

## Milestone 4: Analytics Data in S3-Compatible Storage

### Goal

Store analytics data in an S3-compatible object storage instead of keeping all data inside application services.

### Scope

- start a local S3-compatible server
- create an analytics bucket
- integrate an S3-compatible SDK
- write analytics data into the bucket
- define a simple object naming strategy
- document the storage approach

### Expected Outcome

The `analytics-service` can write structured analytics data into object storage.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 5: Spring Cloud API Gateway

### Goal

Introduce a central API entry point for routing requests to the backend services.

### Scope

- add a Spring Cloud Gateway service
- define routes for the backend services
- route external requests through the gateway
- keep service-specific endpoints behind a central entry point
- prepare the project for later Kubernetes ingress and gateway concepts

### Expected Outcome

The platform has a central API Gateway that routes requests to the internal backend services.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 6: Container Image Publishing

### Goal

Publish container images so that services can be downloaded, reused and deployed without local image builds.

### Scope

- build Docker images for all services
- define image names and tags
- publish images to a container registry
- document pull and run instructions
- prepare images for deployment scenarios
- establish versioned image availability

### Expected Outcome

The services are available as versioned container images and can be deployed without rebuilding the source code locally.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 7: Optimized Java Container Builds

### Goal

Compare and evaluate different approaches for building smaller and more efficient Java container images.

### Scope

- compare classic Dockerfile-based image builds
- evaluate Maven plugin-based image builds
- inspect resulting image sizes
- analyze build time and runtime behavior
- evaluate optimized runtime images
- document trade-offs between simplicity and image optimization

### Expected Outcome

A documented comparison of Java container build approaches with a selected strategy for this project.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 8: Kubernetes

### Goal

Move from local Docker Compose execution towards Kubernetes-based deployment.

### Scope

- create Kubernetes manifests
- deploy services to a local Kubernetes cluster
- configure service discovery inside Kubernetes
- configure internal service communication
- prepare deployment structures for the API Gateway and backend services
- document deployment and troubleshooting steps

### Expected Outcome

The platform can be deployed in a Kubernetes-oriented environment.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Milestone 9: Observability Stack

### Goal

Introduce production-oriented observability concepts for monitoring, logging, tracing and alerting.

### Scope

- expose service metrics
- collect metrics with Prometheus
- visualize metrics in Grafana
- collect logs with Loki
- add distributed tracing with Tempo
- configure basic alerting with Alertmanager
- create initial dashboards
- document observability setup and usage

### Expected Outcome

A basic observability setup that makes service behavior, failures and performance easier to inspect.

### Status

![Planned](https://img.shields.io/badge/Planned-lightgrey)

---

## Long-Term Project Direction

The milestones are designed to grow the project from a basic multi-service backend into a more realistic platform architecture.

The long-term direction includes:

- service-oriented backend development
- asynchronous communication
- external system integration
- object storage
- API Gateway usage
- container image publishing
- optimized Java builds
- Kubernetes deployment
- observability
- DevOps-oriented documentation

## Status Legend

| Status                                                          | Description                                                    |
| --------------------------------------------------------------- | -------------------------------------------------------------- |
| ![Planned](https://img.shields.io/badge/Planned-lightgrey)      | Planned concept or upcoming implementation step                |
| ![In Progress](https://img.shields.io/badge/In%20Progress-blue) | Currently being designed, implemented or documented            |
| ![Done](https://img.shields.io/badge/Done-brightgreen)          | Implemented, tested and documented                             |
| ![Deferred](https://img.shields.io/badge/Deferred-orange)       | Intentionally postponed because another milestone has priority |
