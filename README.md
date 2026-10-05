# Food Ordering App

[![Java](https://img.shields.io/badge/Java-Swing-ED8B00?logo=openjdk&logoColor=white)](https://www.java.com/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![License](https://img.shields.io/github/license/AlakhiarovSalekh/Food-Ordering-App)](LICENSE)
[![Stars](https://img.shields.io/github/stars/AlakhiarovSalekh/Food-Ordering-App?style=social)](https://github.com/AlakhiarovSalekh/Food-Ordering-App/stargazers)

A client-server food ordering application built with Java Swing, Java sockets, and MySQL. The project demonstrates desktop UI development, network communication, persistence, ordering workflows, and reviews.

## Features

- User registration and login
- Java Swing desktop interface
- Browse and manage food items
- Cart and order placement
- Order history
- Ratings and reviews
- User profile management
- Socket-based client/server communication
- MySQL-backed data storage

## Architecture

```text
client/   Java Swing client application
server/   Java server and database integration
```

The client communicates with the server over sockets, while the server handles application logic and persistence.

## Tech Stack

- Java
- Java Swing
- Java Sockets
- MySQL
- Gson

## Getting Started

### Prerequisites

- JDK
- MySQL
- Gson dependency used by the project

Clone the repository:

```bash
git clone https://github.com/AlakhiarovSalekh/Food-Ordering-App.git
cd Food-Ordering-App
```

Configure the database using the SQL/database resources included with the project, then start the server before launching the client. The `server` and `client` directories contain the respective application sources.

## Typical Flow

1. Register or sign in.
2. Browse available food items.
3. Add items to the cart.
4. Submit an order.
5. Review order history.
6. Rate or review completed orders.
7. Manage profile information.

## Contributing

Focused bug fixes, documentation improvements, database/setup corrections, and UI improvements are welcome.

## Author

**Salekh Alakhiarov** · [GitHub](https://github.com/AlakhiarovSalekh)

## License

MIT — see [LICENSE](LICENSE).
