# Rss cloud based application
Used technologies:

- Spring Cloud Config Server
- Spring Cloud Netflix Eureka

[![Build Status](https://travis-ci.org/diniodinev/microservice-application.svg?branch=master)](https://travis-ci.org/diniodinev/microservice-application)

## Modules

- **rss-configuration-cloudserver** - centralized configuration server
- **rss-discovery-server** - Eureka discovery service
- **rss-reader-service** - Spring Boot service exposing RSS feeds

## Building

Use Gradle to build all modules:

```bash
gradle build
```
