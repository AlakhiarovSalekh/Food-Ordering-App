# Food Ordering App V2

This is a food ordering app that allows users to order food from a restaurant. The app is built using Java Swing for the client-side and MySQL for the server-side.

## Features

- Client-side: Java Swing GUI for easy and interactive food ordering.
- Server-side: Java-based server with MySQL database for data management.
- Socket-based connection for seamless communication between client and server.
- Rate and review orders.
- Simulate a mock payment method during checkout.

## Tech Stack

- Client-side:
  - Java Swing
- Server-side:
  - Java
  - MySQL

## Getting Started

### Prerequisites

To run this project locally, you'll need:

- Java Development Kit (JDK)
- MySQL
- GSON package

### Installation

1. Clone the repository:

```bash
git clone https://github.com/Metsehafe-Eyasu/food-ordering-app-v2
```

2. Set up the MySQL database:

   - Create a new database and import the provided SQL dump.

3. Compile and run the server-side:

```bash
cd server
javac Server.java
java Server
```

4. Compile and run the client-side:

```bash
cd client
javac Client.java
java Client
```

## Usage

### 1. Login and Registration

- Open the application.
- Click on the "Login" button if you have an account, or "Register" to create a new one.
- Provide the required credentials and submit.

### 2. Manage Food Items

- Use the options to:
  - Add a new food item.
  - Update existing food items.
  - Delete food items.
  - View the list of available food items.

### 3. Place Order

- Browse through the menu of available food items.
- Select the items you want to order and add them to your cart.
- Proceed to the "Cart" section.
- Confirm your order and submit.

### 4. Simulate Payment

- During the checkout process, a mock payment method is simulated.
- The payment process includes a delay to mimic real-world payment processing.
- The result of the payment (success or failure) is displayed to the user.

### 5. View Order

- You can view your order history in the "History" section.
- This will display a list of all orders you've placed along with their details.

### 6. Rate and Review Orders

- Navigate to the order history page to see past orders.
- Click on the "Leave a Review" button to rate and review an order.

### 7. View Profile

- Access your user profile by clicking on the "Profile" option.
- Here, you can view and edit your personal information.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

We would like to acknowledge the following resources and tools that were used in the development of this project:

- [Java Swing Documentation](https://docs.oracle.com/javase/8/docs/technotes/guides/swing/)
- [MySQL Documentation](https://dev.mysql.com/doc/)
