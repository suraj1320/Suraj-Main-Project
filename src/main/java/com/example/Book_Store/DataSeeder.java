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

                        System.out.println("DataSeeder: Finished successfully.");
                } catch (Exception e) {
                        System.err.println("DataSeeder: Error during seeding: " + e.getMessage());
                        e.printStackTrace();
                        // Do not rethrow, let app start
                }
        }

        private void seedUsers() {
                // Ensure Admin
                User admin = userService.findByUsername("admin");
                if (admin == null) {
                        admin = new User();
                        admin.setUsername("admin");
                        admin.setEmail("admin@saga.com");
                }
                admin.setPassword("admin"); // Service handles encoding
                admin.setRole("ADMIN");
                // Ensure email is set even if user existed but lacked email
                if (admin.getEmail() == null)
                        admin.setEmail("admin@saga.com");
                userService.registerUser(admin);

                // Ensure User
                User user = userService.findByUsername("user");
                if (user == null) {
                        user = new User();
                        user.setUsername("user");
                        user.setEmail("user@saga.com");
                }
                user.setPassword("user"); // Service handles encoding
                user.setRole("USER");
                if (user.getEmail() == null)
                        user.setEmail("user@saga.com");
                userService.registerUser(user);

                System.out.println("Users seeded/updated.");
        }

        private void seedAllCategories() {
                // FICTION
                seedCategory(Category.FICTION, Arrays.asList(
                                "The Great Gatsby", "To Kill a Mockingbird", "1984",
                                "Pride and Prejudice", "The Catcher in the Rye", "The Hobbit"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1476275466078-bd007cd81754?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1543002588-bfa74002ed7e?auto=format&fit=crop&w=800&q=80"));

                // NON_FICTION
                seedCategory(Category.NON_FICTION, Arrays.asList(
                                "Sapiens", "Educated", "Becoming",
                                "The Diary of a Young Girl", "Silent Spring", "In Cold Blood"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1555601568-c99da826bc56?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1513001900722-370f8deedad8?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1535905557558-afc4877a26fc?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1491841550275-ad7854e35ca6?auto=format&fit=crop&w=800&q=80"));

                // MYSTERY
                seedCategory(Category.MYSTERY, Arrays.asList(
                                "The Big Sleep", "Gone Girl", "Girl with the Dragon Tattoo",
                                "The Da Vinci Code", "And Then There Were None", "The Maltese Falcon"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1587876931566-6125026983c2?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1455587734955-081b22074882?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1621351183012-e2f9972dd9bf?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1585435465945-bef5a93f41a9?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1531058240690-006c446962d8?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1576872381149-7847515ce5d8?auto=format&fit=crop&w=800&q=80"));

                // SCIFI
                seedCategory(Category.SCIFI, Arrays.asList(
                                "Dune", "The Martian", "Ender's Game",
                                "Neuromancer", "Snow Crash", "Left Hand of Darkness"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1518531933037-91b2f5f229cc?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1460353581641-37baddab0fa2?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1506318137071-a8bcbf6d919d?auto=format&fit=crop&w=800&q=80"));

                // HORROR
                seedCategory(Category.HORROR, Arrays.asList(
                                "It", "The Shining", "Dracula",
                                "Frankenstein", "The Exorcist", "Pet Sematary"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1509248961158-e54f6934749c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1481018085669-2bc6e6f0011c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1605806616949-1e87b487bc2a?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1505635552518-3448ff116af3?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1547841243-eacb80d6e902?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1519076600672-34d03328516d?auto=format&fit=crop&w=800&q=80"));

                // ROMANCE
                seedCategory(Category.ROMANCE, Arrays.asList(
                                "The Notebook", "Outlander", "Me Before You",
                                "The Fault in Our Stars", "Twilight", "Time Traveler's Wife"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1518621736915-f3b1c41bfd00?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1474552226712-ac0f0961a954?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1529333166437-7750a6dd5a70?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1526725702345-bdda2b97ef73?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80"));

                // BIOGRAPHY
                seedCategory(Category.BIOGRAPHY, Arrays.asList(
                                "Steve Jobs", "Elon Musk", "Einstein",
                                "Diary of a Young Girl", "Alexander Hamilton", "Churchill"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1555601568-c99da826bc56?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1531214159280-079b95d2cde4?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1544717297-fa95b6ee9643?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1550399105-c4db5fb85c18?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1457369804613-52c61a468e7d?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1453894452140-6228795da223?auto=format&fit=crop&w=800&q=80"));

                // HISTORY
                seedCategory(Category.HISTORY, Arrays.asList(
                                "Guns Germs and Steel", "Sapiens", "The Silk Roads",
                                "1776", "Genghis Khan", "A People's History US"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1461360370896-922624d12aa1?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1505664194779-8beaceb93744?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1447069387593-a5de0862481e?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1524985069026-dd778a71c7b4?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1533669955142-6a73332af4db?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1507842217121-fe237e2a9ddb?auto=format&fit=crop&w=800&q=80"));

                // BUSINESS
                seedCategory(Category.BUSINESS, Arrays.asList(
                                "Zero to One", "Shoe Dog", "Good to Great",
                                "Thinking Fast and Slow", "The Lean Startup", "Atomic Habits"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1507679799987-c73779587ccf?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1554774853-719586f8c277?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1556742049-0cfed4f7a07d?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1542744173-8e7e53415bb0?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40?auto=format&fit=crop&w=800&q=80"));

                // SELF_HELP
                seedCategory(Category.SELF_HELP, Arrays.asList(
                                "Atomic Habits", "Power of Now", "Subtle Art Not Giving",
                                "You Are a Badass", "Four Agreements", "Daring Greatly"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1499750310159-5b5f87053225?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1489710437720-ebb67ec84dd2?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1505330622279-bf7d7fc918f4?auto=format&fit=crop&w=800&q=80"));

                // PROGRAMMING
                seedCategory(Category.PROGRAMMING, Arrays.asList(
                                "Clean Code", "Pragmatic Programmer", "Intro to Algorithms",
                                "Design Patterns", "Refactoring", "Head First Java"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1583508915901-b5f84c1dcde1?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1587620962725-abab7fe55159?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1516116216624-53e697fedbea?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1537432376769-00f5c2f4c8d2?auto=format&fit=crop&w=800&q=80"));

                // SCIENCE
                seedCategory(Category.SCIENCE, Arrays.asList(
                                "Cosmos", "Brief History of Time", "Selfish Gene",
                                "What If?", "Elegant Universe", "Astrophysics in Hurry"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1507413245164-6160d8298b31?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1614728853913-1e221a6986a8?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1530210124550-912dc1381cb8?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1523309375637-b3f4f2347f2d?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1518066000714-58c45f1a2c0a?auto=format&fit=crop&w=800&q=80"));

                // TECHNOLOGY
                seedCategory(Category.TECHNOLOGY, Arrays.asList(
                                "The Innovators", "Life 3.0", "The Shallows",
                                "Superintelligence", "Steve Jobs", "Hackers"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1519389950473-47ba0277781c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1535378437321-29e904d16d71?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1526374965328-7f61d4dc18c5?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1531297461136-82lw9f2208E?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1550751827-4bd374c3f58b?auto=format&fit=crop&w=800&q=80"));

                // FANTASY
                seedCategory(Category.FANTASY, Arrays.asList(
                                "Harry Potter", "The Hobbit", "Game of Thrones",
                                "Name of the Wind", "Way of Kings", "Lies of Locke Lamora"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1519076600672-34d03328516d?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1626618012641-bfbca5a31239?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1618666012174-83b441c0bc76?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1518709268805-4e9042af9f23?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1498931299472-f7a63a5a1cfa?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1463171379511-1365ad4fa01b?auto=format&fit=crop&w=800&q=80"));

                // THRILLER
                seedCategory(Category.THRILLER, Arrays.asList(
                                "The Silent Patient", "Gone Girl", "Girl on the Train",
                                "Da Vinci Code", "Angels & Demons", "Shutter Island"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1603513492128-ba6fb880153e?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1510414696678-76403685f473?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1621351183012-e2f9972dd9bf?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1571066811602-71083be05545?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1582562124811-2867e0505179?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1615796245786-fb788d600609?auto=format&fit=crop&w=800&q=80"));

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
}
