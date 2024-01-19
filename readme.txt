Conference Room Booking Application
This is a simple conference room booking application developed using Spring Boot. The application allows users to book conference rooms based on specified criteria. Below, you'll find instructions on how to set up and run the application.

Prerequisites
Make sure you have the following installed on your system:

Java 17
Maven
H2 Database (for testing purposes)
Getting Started
Clone the repository:

bash
Copy code
git clone <repository-url>
cd assessment
Build the application:

bash
Copy code
mvn clean install
Run the application:

bash
Copy code
java -jar target/assessment-0.0.1-SNAPSHOT.jar
The application will start, and you can access it at http://localhost:8080.

Database Configuration
The application uses an H2 in-memory database for testing purposes. If you want to configure a different database, modify the application.yml file accordingly.

You can customize certain configurations using the application.yml file. For example, the booking interval is set to 15 minutes by default.

API Endpoints
Book Room:

Endpoint: POST /api/conference/book
Request Body: BookingRequestDTO
Check Available Rooms:

Endpoint: POST /api/conference/available
Request Body: BookingRequestDTO
Get All Rooms:

Endpoint: GET /api/conference/find-all-rooms
Get All Bookings:

Endpoint: GET /api/conference/find-all-bookings


Happy conferencing! 🎉