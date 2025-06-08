# Tracking Number Generator API

A scalable, efficient, and concurrent-safe RESTful API to generate unique tracking numbers for parcels.

---

## Table of Contents
- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [API Specification](#api-specification)
- [Getting Started](#getting-started)
- [Testing the API](#testing-the-api)
---

## Overview

This project provides a RESTful API endpoint to generate unique parcel tracking numbers. It validates inputs strictly and produces tracking numbers matching the pattern `^[A-Z0-9]{1,16}$`. The service is stateless, efficient, and designed to scale horizontally.

---

## Tech Stack

- Java 17
- Spring Boot 3.5.0
- Maven
- NanoId for tracking number generation
- SLF4J with Logback for logging

---

## API Specification

### Endpoint 
GET /next-tracking-number


### Query Parameters

| Parameter             | Type   | Description                                    | Example                         |
|-----------------------|--------|------------------------------------------------|---------------------------------|
| origin_country_id     | String | Origin country code (ISO 3166-1 alpha-2)       | "MY"                            |
| destination_country_id| String | Destination country code (ISO 3166-1 alpha-2)  | "ID"                            |
| weight                | String | Weight in kilograms (up to 3 decimal places)   | "1.234"                        |
| created_at            | String | Creation timestamp (RFC 3339 format)            | "2018-11-20T19:29:32+08:00"   |
| customer_id           | String | Customer UUID                                   | "de619854-b59b-425e-9db4-943979e1bd49" |
| customer_name         | String | Customer name                                   | "RedBox Logistics"             |
| customer_slug         | String | Customer slug (kebab-case)                      | "redbox-logistics"             |

### Response

```json
{
  "tracking_number": "A1B2C3D4E5F6G7H8",
  "created_at": "2025-06-08T18:00:00+05:30"
}
```

### Error Response
```json
{
  "timestamp": "2025-06-08T18:48:32.746451+05:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid created_at timestamp"
}
```

## Build and Run
### Clone the repository
```bash
git clone <repository-url>
cd tracking-number-generator
```

### Build the project
```bash
mvn clean package
```

### Run the application
```bash
mvn spring-boot:run
```

The API will be available at:
http://localhost:8080/next-tracking-number



## Testing the API
Example request using curl:
```bash
curl --request GET --url 'http://localhost:8080/next-tracking-number?origin_country_id=MY&destination_country_id=ID&weight=1.234&created_at=2018-11-20T19%3A29%3A32Z&customer_id=de619854-b59b-425e-9db4-943979e1bd49&customer_name=RedBox%20Logistics&customer_slug=redbox-logistics'```