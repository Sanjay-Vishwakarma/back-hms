This is a Hotel Management Service Backend built with Java 8 + Spring Boot + Spring Data JPA + H2 database.
It manages:

Hotel rooms
Guests
Bookings
Room availability
Check-in / check-out
Booking cancellation

Revenue summary
It uses an in-memory H2 database, so data resets when the app restarts. Some sample rooms and guests are automatically added when the app starts.
API List

Room APIs
GET    /api/rooms
GET    /api/rooms/available
POST   /api/rooms
PUT    /api/rooms/{id}
DELETE /api/rooms/{id}
Total: 5

Guest APIs
GET  /api/guests
POST /api/guests
PUT  /api/guests/{id}
Total: 3

Booking APIs
GET  /api/bookings
POST /api/bookings
POST /api/bookings/{id}/check-in
POST /api/bookings/{id}/check-out
POST /api/bookings/{id}/cancel
GET  /api/bookings/summary
Total: 6

H2 Database Console
GET /h2-console
Total: 1

Spring Boot Default Error API
/error
Total: 1

So:
Room APIs:     5
Guest APIs:    3
Booking APIs:  6
H2 Console:    1
Error API:     1
Total:        16

The main business APIs are 14, excluding /h2-console and /error.