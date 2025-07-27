# Rss cloud based application

This repository contains a set of Spring Boot microservices for collecting and exposing RSS news feeds. The services share their configuration through a central server and discover each other via Eureka so they can run independently or with the provided Docker compose file.

Used technologies:

- Spring Cloud Config Server
- Spring Cloud Netflix Eureka

[![CI](https://github.com/diniodinev/microservice-application/actions/workflows/gradle.yml/badge.svg)](https://github.com/diniodinev/microservice-application/actions/workflows/gradle.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=diniodinev-github_sitereader&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=diniodinev-github_sitereader)

## Modules

### rss-configuration-cloudserver
A small service that enables the Spring Cloud Config Server. Configuration files are served from a Git repository and all other modules pull their settings from here.

### rss-discovery-server
Hosts a Netflix Eureka registry so that each service can register and resolve the others. The application is only a few lines of code enabling the server and defining its ports in `application.yml`.

### rss-reader-service
The main business module. It crawls various news sites, stores the parsed articles in an embedded HSQL database and exposes REST endpoints that return the data as RSS feeds. It includes custom extraction classes, JPA repositories, Swagger documentation and utilities for database backups.

## Building

Use Gradle to build all modules:

```bash
gradle build
```
