# Hotel Management Service

Spring Boot backend for a hotel management system using Java 8 compatible dependencies.

## Features

- Room inventory with room type, capacity, price, and soft delete
- Guest registration and update
- Booking creation with capacity and date-overlap checks
- Check-in, check-out, and cancellation workflow
- Availability search by date range and optional room type
- Revenue summary
- Bean validation and central API error responses
- H2 in-memory database with seed rooms and guests
- Java 8 style code using streams, lambdas, method references, `Optional`, and `java.time`

## Run

```bash
./mvnw spring-boot:run
```

Base URL:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:hotel_db
```

## API

### Rooms

```http
GET /api/rooms
GET /api/rooms/available?checkInDate=2026-07-01&checkOutDate=2026-07-03&type=DOUBLE
POST /api/rooms
PUT /api/rooms/{id}
DELETE /api/rooms/{id}
```

Create room body:

```json
{
  "roomNumber": "401",
  "type": "DELUXE",
  "capacity": 3,
  "pricePerNight": 4500.00
}
```

Room types:

```text
SINGLE, DOUBLE, DELUXE, SUITE
```

### Guests

```http
GET /api/guests
POST /api/guests
PUT /api/guests/{id}
```

Create guest body:

```json
{
  "fullName": "Rohan Mehta",
  "email": "rohan@example.com",
  "phone": "9988776655",
  "idProof": "AADHAAR-9001"
}
```

### Bookings

```http
GET /api/bookings
POST /api/bookings
POST /api/bookings/{id}/check-in
POST /api/bookings/{id}/check-out
POST /api/bookings/{id}/cancel
GET /api/bookings/summary
```

Create booking body:

```json
{
  "roomId": 1,
  "guestId": 1,
  "checkInDate": "2026-07-01",
  "checkOutDate": "2026-07-03",
  "numberOfGuests": 1
}
```

Booking statuses:

```text
RESERVED, CHECKED_IN, CHECKED_OUT, CANCELLED
```

## Test

```bash
./mvnw test
```
