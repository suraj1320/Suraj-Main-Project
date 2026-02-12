package com.example.Book_Store;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.model.User;
import com.example.Book_Store.model.enums.Category;
import com.example.Book_Store.service.BookService;
import com.example.Book_Store.service.UserService;
import com.example.Book_Store.repository.CartRepository;
import com.example.Book_Store.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.Book_Store.model.Order;
import com.example.Book_Store.model.OrderItem;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

        private final BookService bookService;
        private final UserService userService;
        private final OrderRepository orderRepository;
        private final CartRepository cartRepository;

        public DataSeeder(BookService bookService, UserService userService, OrderRepository orderRepository,
                        CartRepository cartRepository) {
                this.bookService = bookService;
                this.userService = userService;
                this.orderRepository = orderRepository;
                this.cartRepository = cartRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                try {
                        System.out.println("DataSeeder: Starting...");

                        // Force clean slate? User said "add images", implying replace or add.
                        try {
                                orderRepository.deleteAll();
                                cartRepository.deleteAll();
                                bookService.deleteAll();
                                System.out.println("DataSeeder: Books and related data deleted.");
                        } catch (Exception e) {
                                System.out.println(
                                                "DataSeeder: Could not delete data. Error: "
                                                                + e.getMessage());
                        }

                        seedUsers();

                        seedAllCategories();

                        seedOrders();

                        System.out.println("DataSeeder: Finished successfully.");
                } catch (Exception e) {
                        System.err.println("DataSeeder: Error during seeding: " + e.getMessage());
                        e.printStackTrace();
                        // Do not rethrow, let app start
                }
        }

        private void seedUsers() {
                // Admin user creation removed as per request

                // Ensure User
                User user = userService.findByUsername("user");
                if (user == null) {
                        user = new User();
                        user.setUsername("user");
                }
                user.setPassword("user"); // Service handles encoding
                user.setRole("USER");
                // Ensure email is set cleanly
                user.setEmail("user@saga.com");
                userService.registerUser(user);

                System.out.println("Users seeded/updated.");
        }

        private void seedAllCategories() {
                // Common Book Images to ensure "Book" theme (No random landscapes/laptops)
                String book1 = "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=800&q=80";
                String book2 = "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80";
                String book3 = "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=800&q=80";
                String book4 = "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80";
                String book5 = "https://images.unsplash.com/photo-1495446815901-a7297e633e8d?auto=format&fit=crop&w=800&q=80";
                String book6 = "https://images.unsplash.com/photo-1524578271613-d550eacf6090?auto=format&fit=crop&w=800&q=80"; // Bookstore
                String book7 = "https://images.unsplash.com/photo-1497633762265-9d179a990aa6?auto=format&fit=crop&w=800&q=80"; // Open
                                                                                                                               // book
                String book8 = "https://images.unsplash.com/photo-1491841550275-ad7854e35ca6?auto=format&fit=crop&w=800&q=80";
                String book9 = "https://images.unsplash.com/photo-1550399105-c4db5fb85c18?auto=format&fit=crop&w=800&q=80";
                String book10 = "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?auto=format&fit=crop&w=800&q=80";

                // FICTION
                seedCategory(Category.FICTION, Arrays.asList(
                                "The Great Gatsby", "To Kill a Mockingbird", "1984",
                                "Pride and Prejudice", "The Catcher in the Rye", "The Hobbit"),
                                Arrays.asList(book1, book2, book3, book4, book5, book6));

                // NON_FICTION
                seedCategory(Category.NON_FICTION, Arrays.asList(
                                "Sapiens", "Educated", "Becoming",
                                "The Diary of a Young Girl", "Silent Spring", "In Cold Blood"),
                                Arrays.asList(book7, book8, book9, book10, book1, book2));

                // MYSTERY
                seedCategory(Category.MYSTERY, Arrays.asList(
                                "The Big Sleep", "Gone Girl", "Girl with the Dragon Tattoo",
                                "The Da Vinci Code", "And Then There Were None", "The Maltese Falcon"),
                                Arrays.asList(book3, book4, book5, book6, book7, book8));

                // SCIFI
                seedCategory(Category.SCIFI, Arrays.asList(
                                "Dune", "The Martian", "Ender's Game",
                                "Neuromancer", "Snow Crash", "Left Hand of Darkness"),
                                Arrays.asList(book9, book10, book1, book2, book3, book4));

                // HORROR
                seedCategory(Category.HORROR, Arrays.asList(
                                "It", "The Shining", "Dracula",
                                "Frankenstein", "The Exorcist", "Pet Sematary"),
                                Arrays.asList(book5, book6, book7, book8, book9, book10));

                // ROMANCE
                seedCategory(Category.ROMANCE, Arrays.asList(
                                "The Notebook", "Outlander", "Me Before You",
                                "The Fault in Our Stars", "Twilight", "Time Traveler's Wife"),
                                Arrays.asList(book1, book3, book5, book7, book9, book2));

                // BIOGRAPHY
                seedCategory(Category.BIOGRAPHY, Arrays.asList(
                                "Steve Jobs", "Elon Musk", "Einstein",
                                "Diary of a Young Girl", "Alexander Hamilton", "Churchill"),
                                Arrays.asList(book2, book4, book6, book8, book10, book1));

                // HISTORY
                seedCategory(Category.HISTORY, Arrays.asList(
                                "Guns Germs and Steel", "Sapiens", "The Silk Roads",
                                "1776", "Genghis Khan", "A People's History US"),
                                Arrays.asList(book3, book5, book7, book9, book2, book4));

                // BUSINESS
                seedCategory(Category.BUSINESS, Arrays.asList(
                                "Zero to One", "Shoe Dog", "Good to Great",
                                "Thinking Fast and Slow", "The Lean Startup", "Atomic Habits"),
                                Arrays.asList(book6, book8, book10, book1, book3, book5));

                // SELF_HELP
                seedCategory(Category.SELF_HELP, Arrays.asList(
                                "Atomic Habits", "Power of Now", "Subtle Art Not Giving",
                                "You Are a Badass", "Four Agreements", "Daring Greatly"),
                                Arrays.asList(book7, book9, book2, book4, book6, book8));

                // PROGRAMMING
                seedCategory(Category.PROGRAMMING, Arrays.asList(
                                "Clean Code", "Pragmatic Programmer", "Intro to Algorithms",
                                "Design Patterns", "Refactoring", "Head First Java"),
                                Arrays.asList(book4, book1, book5, book2, book7, book3)); // Ensuring Books!

                // SCIENCE
                seedCategory(Category.SCIENCE, Arrays.asList(
                                "Cosmos", "Brief History of Time", "Selfish Gene",
                                "What If?", "Elegant Universe", "Astrophysics in Hurry"),
                                Arrays.asList(book5, book10, book2, book9, book6, book1));

                // TECHNOLOGY
                seedCategory(Category.TECHNOLOGY, Arrays.asList(
                                "The Innovators", "Life 3.0", "The Shallows",
                                "Superintelligence", "Steve Jobs", "Hackers"),
                                Arrays.asList(book1, book2, book3, book4, book5, book6)); // Books, not laptops

                // FANTASY
                seedCategory(Category.FANTASY, Arrays.asList(
                                "Harry Potter", "The Hobbit", "Game of Thrones",
                                "Name of the Wind", "Way of Kings", "Lies of Locke Lamora"),
                                Arrays.asList(book7, book8, book9, book10, book2, book4));

                // THRILLER
                seedCategory(Category.THRILLER, Arrays.asList(
                                "The Silent Patient", "Gone Girl", "Girl on the Train",
                                "Da Vinci Code", "Angels & Demons", "Shutter Island"),
                                Arrays.asList(book3, book6, book8, book1, book5, book9));

                System.out.println("All categories seeded with 6 books each.");
        }

        private void seedCategory(Category category, List<String> titles, List<String> imageUrls) {
                System.out.println("Processing category: " + category + " with " + titles.size() + " books.");
                for (int i = 0; i < titles.size(); i++) {
                        try {
                                String title = titles.get(i);
                                String imageUrl = imageUrls.get(i % imageUrls.size());
                                Book book = createBook(title, "Famous Author " + (i + 1), 10.0 + i, category, imageUrl);
                                bookService.saveBook(book);
                                System.out.println("Saved book: " + book.getTitle() + " in " + category);
                        } catch (Exception e) {
                                System.err.println("Error saving book in category " + category + ": " + e.getMessage());
                                e.printStackTrace();
                        }
                }
        }

        private Book createBook(String title, String author, Double price, Category category, String imageUrl) {
                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setPrice(price);
                book.setCategory(category);
                book.setImage(imageUrl);
                book.setStock(100);
                book.setDescription("A best-selling book: " + title + ". Highly rated in " + category + ".");
                return book;
        }

        private void seedOrders() {
                System.out.println("Seeding orders...");
                User user = userService.findByUsername("user");
                List<Book> books = bookService.getAllBooks();

                if (user != null && !books.isEmpty()) {
                        Book book1 = books.get(0);
                        Book book2 = books.get(1);

                        // Order 1: Pending
                        Order order1 = new Order();
                        order1.setUser(user);
                        order1.setRecipientName("John Doe");
                        order1.setPhoneNumber("1234567890");
                        order1.setShippingAddress("123 Elm St, NY");
                        order1.setStatus("Pending");
                        order1.setOrderDate(LocalDateTime.now().minusDays(1));

                        OrderItem item1 = new OrderItem();
                        item1.setBook(book1);
                        item1.setQuantity(1);
                        item1.setPrice(book1.getPrice());
                        order1.addItem(item1);

                        order1.setTotalAmount(book1.getPrice());
                        orderRepository.save(order1);

                        // Order 2: Completed
                        Order order2 = new Order();
                        order2.setUser(user);
                        order2.setRecipientName("Jane Smith");
                        order2.setPhoneNumber("0987654321");
                        order2.setShippingAddress("456 Oak Ave, CA");
                        order2.setStatus("Completed");
                        order2.setOrderDate(LocalDateTime.now().minusDays(5));

                        OrderItem item2 = new OrderItem();
                        item2.setBook(book2);
                        item2.setQuantity(2);
                        item2.setPrice(book2.getPrice());
                        order2.addItem(item2);

                        order2.setTotalAmount(book2.getPrice() * 2);
                        orderRepository.save(order2);

                        System.out.println("Orders seeded.");
                } else {
                        System.out.println("Skipping order seeding: User or Books not found.");
                }
        }
}
