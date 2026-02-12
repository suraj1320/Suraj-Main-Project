# 📚 SAGA | The Ultimate Bookstore

![Project Status](https://img.shields.io/badge/Status-Active-brightgreen) ![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)

**SAGA** is a premium, full-stack online bookstore application built to deliver a world-class shopping experience. It combines a stunning "Glassmorphism" UI with a robust Spring Boot backend, featuring secure authentication, comprehensive inventory management, and a dynamic shopping cart system.

---

## 📑 Table of Contents
1.  [About the Project](#-about-the-project)
2.  [Key Features](#-key-features)
3.  [Technology Stack](#-technology-stack)
4.  [Prerequisites](#-prerequisites)
5.  [Installation & Setup](#-installation--setup)
6.  [Application Walkthrough](#-application-walkthrough)
7.  [Project Structure](#-project-structure)
8.  [Configuration](#-configuration)
9.  [Troubleshooting](#-troubleshooting)

---

## 📖 About the Project

SAGA was designed to solve the problem of clunky, outdated bookstore interfaces. It provides:
*   **Visually Immersive Experience:** High-quality imagery and modern design principles.
*   **Seamless Navigation:** Intuitive browsing by categories.
*   **Admin Control:** Simplified control over the book inventory to Add, Edit, or Remove books easily.
*   **Contact & Support:** Built-in contact form for user queries.

---

## 🚀 Key Features

### 👤 User Module
*   **User Authentication:** Secure Sign Up and Login functionality.
*   **Homepage:** Dynamic slider showcasing featured collections.
*   **Shop Page:** Filter books by 15+ categories (Fiction, Sci-Fi, Tech, etc.).
*   **Unique Imagery:** **Distinct book covers** for every category to ensure a rich visual experience.
*   **Product Details:** View pricing, authors, and descriptions.
*   **Shopping Cart:** Add/Remove items and view total cost in real-time.
*   **Order History:** Track past purchases (My Orders) with a clean, simplified view.
*   **Contact Page:** Dedicated page for reaching out to support or visiting the physical store.

### 🛡️ Admin Module
*   **Role-Based Access:** Restricted access to authorized admins only.
*   **Simplified Dashboard:** Focused entirely on **Book Inventory Management**.
*   **Inventory Management:** 
    *   **Add Books:** Form to input title, author, price, category, and image URL.
    *   **Edit Books:** Update details of existing books.
    *   **Delete Books:** Remove outdated stock with one click.
*   **Data Seeding:** Automatic population of curated books on startup with high-quality, category-specific images.

---

## 🛠 Technology Stack

### Backend
*   **Language:** Java 17
*   **Framework:** Spring Boot 3.0+
*   **Build Tool:** Maven
*   **Database:** H2 In-Memory (Default) / MySQL (Compatible)
*   **Security:** Spring Security (Session-based Auth)

### Frontend
*   **Template Engine:** Thymeleaf
*   **Styling:** Custom CSS3 (Glassmorphism, Flexbox, Grid)
*   **Scripting:** Vanilla JavaScript
*   **Icons:** Google Fonts & Emoji support

---

## 📋 Prerequisites

Before you begin, ensure you have the following installed on your machine:
*   [Java Development Kit (JDK) 17](https://www.oracle.com/java/technologies/downloads/) or higher.
*   [Maven](https://maven.apache.org/download.cgi) (or use the included `mvnw` wrapper).
*   **IDE:** IntelliJ IDEA, Eclipse, or VS Code (Recommended).

---

## ⚙️ Installation & Setup

Follow these steps to get the project running locally:

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/saga-bookstore.git
cd saga-bookstore
```

### 2. Configure Database (Optional)
*By default, the app uses H2 In-Memory database. No configuration needed.*
*   To touch the database console, go to: `http://localhost:8080/h2-console`
*   **JDBC URL:** `jdbc:h2:mem:testdb`
*   **User:** `sa`
*   **Password:** *(Leave Empty)*

### 3. Build the Application
```bash
mvn clean install
```

### 4. Run the Server
```bash
mvn spring-boot:run
```
*Alternatively, run the `BookStoreApplication.java` file directly from your IDE.*

### 5. Access the App
Open your web browser and navigate to:
👉 **http://localhost:8080**

---

## 🚶 Application Walkthrough

### 🔐 Secure Accounts (Pre-Seeded)
The application automatically creates these users on startup:

| Role | Username | Password | Permissions |
| :--- | :--- | :--- | :--- |
| **ADMIN** | `admin` | `admin` | Full Access, Admin Panel, Manage Books |
| **USER** | `user` | `user` | Shop, Cart, Checkout, Contact |

### How to use:
1.  **Register/Login:** Use the buttons in the navbar.
2.  **Admin Panel:** Login as `admin`, then click the red **Admin Panel** button in the header.
3.  **Shop:** Browse categories. The database is seeded with **unique books per category**.
4.  **Contact:** Use the 'Contact' link in the navbar to send messages.

---

## 📂 Project Structure

```
com.example.Book_Store
├── controller
│   ├── AdminController.java    # Handles Admin dashboard & inventory
│   ├── AuthController.java     # Handles Login/Register logic
│   ├── HomeController.java     # Handles Home, Shop, About & Contact pages
│   └── OrderController.java    # Handles Cart & Checkout
├── model
│   ├── Book.java               # Book Entity
│   ├── User.java               # User Entity (Role: USER/ADMIN)
│   └── enums/Category.java     # Fixed list of book genres
├── repository
│   ├── BookRepository.java     # Database ops for Books
│   └── UserRepository.java     # Database ops for Users
├── DataSeeder.java             # 🌱 Auto-populates Users & Books with unique images
└── BookStoreApplication.java   # Main Entry Point
```

---

## 🔧 Configuration

### Changing Images
The images are currently hosted via Unsplash URLs in `DataSeeder.java`.
To use local images:
1.  Put images in `src/main/resources/static/images/`.
2.  Update `DataSeeder.java` to use paths like `/images/my-book.jpg`.

---

## ❓ Troubleshooting

**Q: The images are not loading.**
A: Ensure you have an active internet connection as images are fetched from Unsplash.

**Q: I added a book but it disappeared after restart.**
A: The app uses an In-Memory Database (H2). Data resets on restart. To verify persistence, check the `DataSeeder` logic or connect a MySQL database.

**Q: I can't see the Admin Panel.**
A: Ensure you are logged in with username `admin`. The button is visible in the header.

---

### 🎉 Enjoy SAGA Bookstore!
*Built with ❤️ for Book Lovers.*
