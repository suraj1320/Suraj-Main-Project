package com.example.Book_Store;

import com.example.Book_Store.model.Book;
import com.example.Book_Store.model.User;
import com.example.Book_Store.model.enums.Category;
import com.example.Book_Store.repository.BookRepository;
import com.example.Book_Store.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

        private final BookRepository bookRepository;
        private final UserRepository userRepository;

        public DataSeeder(BookRepository bookRepository, UserRepository userRepository) {
                this.bookRepository = bookRepository;
                this.userRepository = userRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                // Force clean slate? User said "add images", implying replace or add.
                // To ensure we have exactly 12 per category and no duplicates of old bad ones,
                // we should clear.
                // bookRepository.deleteAll();

                if (userRepository.count() == 0) {
                        seedUsers();
                }

                if (bookRepository.count() == 0) {
                        seedAllCategories();
                }
        }

        private void seedUsers() {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin");
                admin.setRole("ADMIN");
                userRepository.save(admin);

                User user = new User();
                user.setUsername("user");
                user.setPassword("user");
                user.setRole("USER");
                userRepository.save(user);

                System.out.println("Users seeded.");
        }

        private void seedAllCategories() {
                // FICTION
                seedCategory(Category.FICTION, Arrays.asList(
                                "The Great Gatsby", "To Kill a Mockingbird", "1984", "Pride and Prejudice",
                                "The Catcher in the Rye", "The Hobbit", "Fahrenheit 451", "Jane Eyre",
                                "Animal Farm", "Lord of the Flies", "Brave New World", "Wuthering Heights"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1544947950-fa07a98d237f?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1476275466078-bd007cd81754?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=800&q=80"));

                // NON_FICTION
                seedCategory(Category.NON_FICTION, Arrays.asList(
                                "Sapiens", "Educated", "Becoming", "The Diary of a Young Girl",
                                "Silent Spring", "In Cold Blood", "The Immortal Life of H. Lacks", "Into the Wild",
                                "The Wright Brothers", "Unbroken", "The Glass Castle", "Just Mercy"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1589829085413-56de8ae18c73?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1555601568-c99da826bc56?auto=format&fit=crop&w=800&q=80"));

                // MYSTERY
                seedCategory(Category.MYSTERY, Arrays.asList(
                                "The Big Sleep", "Gone Girl", "Girl with the Dragon Tattoo", "The Da Vinci Code",
                                "And Then There Were None", "The Maltese Falcon", "Sherlock Holmes", "Rebecca",
                                "In the Woods", "The Silent Patient", "Sharp Objects", "Woman in the Window"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1587876931566-6125026983c2?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1455587734955-081b22074882?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1621351183012-e2f9972dd9bf?auto=format&fit=crop&w=800&q=80"));

                // SCIFI
                seedCategory(Category.SCIFI, Arrays.asList(
                                "Dune", "The Martian", "Ender's Game", "Neuromancer",
                                "Snow Crash", "Left Hand of Darkness", "Foundation", "Hyperion",
                                "Jurassic Park", "Ready Player One", "The Time Machine", "War of the Worlds"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1451187580459-43490279c0fa?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80"));

                // HORROR
                seedCategory(Category.HORROR, Arrays.asList(
                                "It", "The Shining", "Dracula", "Frankenstein",
                                "The Exorcist", "Pet Sematary", "Bird Box", "World War Z",
                                "Carrie", "Misery", "Haunting of Hill House", "Doctor Sleep"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1509248961158-e54f6934749c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1481018085669-2bc6e6f0011c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1605806616949-1e87b487bc2a?auto=format&fit=crop&w=800&q=80"));

                // ROMANCE
                seedCategory(Category.ROMANCE, Arrays.asList(
                                "The Notebook", "Outlander", "Me Before You", "The Fault in Our Stars",
                                "Twilight", "Time Traveler's Wife", "Fifty Shades of Grey", "Pride and Prejudice",
                                "The Hating Game", "Beach Read", "Red, White & Royal Blue", "The Proposal"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1518621736915-f3b1c41bfd00?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1474552226712-ac0f0961a954?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1516979187457-637abb4f9353?auto=format&fit=crop&w=800&q=80"));

                // BIOGRAPHY
                seedCategory(Category.BIOGRAPHY, Arrays.asList(
                                "Steve Jobs", "Elon Musk", "Einstein", "Diary of a Young Girl",
                                "Alexander Hamilton", "Churchill", "Leonardo da Vinci", "Becoming",
                                "Long Walk to Freedom", "I Know Why Caged Bird Sings", "Bossypants", "Born a Crime"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1555601568-c99da826bc56?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1531214159280-079b95d2cde4?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1544717297-fa95b6ee9643?auto=format&fit=crop&w=800&q=80"));

                // HISTORY
                seedCategory(Category.HISTORY, Arrays.asList(
                                "Guns Germs and Steel", "Sapiens", "The Silk Roads", "1776",
                                "Genghis Khan", "A People's History US", "Band of Brothers", "Devil in White City",
                                "Team of Rivals", "The Guns of August", "Stalingrad", "SPQR"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1461360370896-922624d12aa1?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1505664194779-8beaceb93744?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1447069387593-a5de0862481e?auto=format&fit=crop&w=800&q=80"));

                // BUSINESS
                seedCategory(Category.BUSINESS, Arrays.asList(
                                "Zero to One", "Shoe Dog", "Good to Great", "Thinking Fast and Slow",
                                "The Lean Startup", "Atomic Habits", "Rich Dad Poor Dad", "How to Win Friends",
                                "Deep Work", "Start with Why", "Principles", "4-Hour Work Week"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1507679799987-c73779587ccf?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1460925895917-afdab827c52f?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1554774853-719586f8c277?auto=format&fit=crop&w=800&q=80"));

                // SELF_HELP
                seedCategory(Category.SELF_HELP, Arrays.asList(
                                "Atomic Habits", "Power of Now", "Subtle Art Not Giving", "You Are a Badass",
                                "Four Agreements", "Daring Greatly", "Mindset", "Grit",
                                "7 Habits Effective People", "Awaken Giant Within", "Can't Hurt Me", "Make Your Bed"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1499750310159-5b5f87053225?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1506126613408-eca07ce68773?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1489710437720-ebb67ec84dd2?auto=format&fit=crop&w=800&q=80"));

                // PROGRAMMING
                seedCategory(Category.PROGRAMMING, Arrays.asList(
                                "Clean Code", "Pragmatic Programmer", "Intro to Algorithms", "Design Patterns",
                                "Refactoring", "Head First Java", "Effective Java", "Code Complete",
                                "Cracking Coding Interview", "Mythical Man-Month", "SICP", "Python Crash Course"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1583508915901-b5f84c1dcde1?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1555066931-4365d14bab8c?auto=format&fit=crop&w=800&q=80"));

                // SCIENCE
                seedCategory(Category.SCIENCE, Arrays.asList(
                                "Cosmos", "Brief History of Time", "Selfish Gene", "What If?",
                                "Elegant Universe", "Astrophysics in Hurry", "The Gene", "Silent Spring",
                                "Man Who Knew Infinity", "Double Helix", "Origin of Species",
                                "Short History Nearly Everything"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1507413245164-6160d8298b31?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1614728853913-1e221a6986a8?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1532012197267-da84d127e765?auto=format&fit=crop&w=800&q=80"));

                // TECHNOLOGY
                seedCategory(Category.TECHNOLOGY, Arrays.asList(
                                "The Innovators", "Life 3.0", "The Shallows", "Superintelligence",
                                "Steve Jobs", "Hackers", "Soul of New Machine", "Ghost in the Wires",
                                "Permanent Record", "Digital Gold", "AI Superpowers", "Loonshots"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1519389950473-47ba0277781c?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1535378437321-29e904d16d71?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1512820790803-83ca734da794?auto=format&fit=crop&w=800&q=80"));

                // FANTASY
                seedCategory(Category.FANTASY, Arrays.asList(
                                "Harry Potter", "The Hobbit", "Game of Thrones", "Name of the Wind",
                                "Way of Kings", "Lies of Locke Lamora", "Mistborn", "American Gods",
                                "Percy Jackson", "Chronicles of Narnia", "Wheel of Time", "The Witcher"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1519076600672-34d03328516d?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1626618012641-bfbca5a31239?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1618666012174-83b441c0bc76?auto=format&fit=crop&w=800&q=80"));

                // THRILLER
                seedCategory(Category.THRILLER, Arrays.asList(
                                "The Silent Patient", "Gone Girl", "Girl on the Train", "Da Vinci Code",
                                "Angels & Demons", "Shutter Island", "Behind Closed Doors", "Woman in Cabin 10",
                                "Before I Go to Sleep", "Dark Matter", "The Firm", "Pelican Brief"),
                                Arrays.asList(
                                                "https://images.unsplash.com/photo-1603513492128-ba6fb880153e?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1510414696678-76403685f473?auto=format&fit=crop&w=800&q=80",
                                                "https://images.unsplash.com/photo-1621351183012-e2f9972dd9bf?auto=format&fit=crop&w=800&q=80"));

                System.out.println("All categories seeded with 12 books each.");
        }

        private void seedCategory(Category category, List<String> titles, List<String> imageUrls) {
                for (int i = 0; i < titles.size(); i++) {
                        String title = titles.get(i);
                        String imageUrl = imageUrls.get(i % imageUrls.size());
                        Book book = createBook(title, "Famous Author " + (i + 1), 10.0 + i, category, imageUrl);
                        bookRepository.save(book);
                }
        }

        private Book createBook(String title, String author, Double price, Category category, String imageUrl) {
                Book book = new Book();
                book.setTitle(title);
                book.setAuthor(author);
                book.setPrice(price);
                book.setCategory(category);
                book.setImage(imageUrl);
                book.setDescription("A best-selling book: " + title + ". Highly rated in " + category + ".");
                return book;
        }
}
