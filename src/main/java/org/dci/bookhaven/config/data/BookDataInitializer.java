package org.dci.bookhaven.config.data;

import org.dci.bookhaven.model.Book;
import org.dci.bookhaven.model.Category;
import org.dci.bookhaven.service.BookService;
import org.dci.bookhaven.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
@Order(DataInitOrder.BOOK)
public class BookDataInitializer implements CommandLineRunner {

    private final BookService bookService;

    private final CategoryService categoryService;

    public BookDataInitializer(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        createBook(
                "Harry Potter and the Philosopher's Stone",
                "J.K. Rowling",
                "The first book in the Harry Potter series, introducing Harry and his journey into the wizarding world.",
                "hp1.jpg",
                "9780747532743",
                "English",
                223,
                10.99,
                LocalDate.of(2024, 9, 1),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Chamber of Secrets",
                "J.K. Rowling",
                "The second book in the Harry Potter series, where Harry returns to Hogwarts and faces new challenges and mysteries.",
                "hp2.jpg",
                "9780747538493",
                "English",
                251,
                11.99,
                LocalDate.of(2024, 9, 2),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Prisoner of Azkaban",
                "J.K. Rowling",
                "The third book in the Harry Potter series, where Harry learns about his past and faces the dark forces of the wizarding world.",
                "hp3.jpg",
                "9780747542155",
                "English",
                317,
                12.99,
                LocalDate.of(2024, 9, 3),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Goblet of Fire",
                "J.K. Rowling",
                "The fourth book in the Harry Potter series, where Harry competes in the Triwizard Tournament and faces greater dangers.",
                "hp4.jpg",
                "9780747546245",
                "English",
                636,
                14.99,
                LocalDate.of(2024, 9, 4),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Order of the Phoenix",
                "J.K. Rowling",
                "The fifth book in the Harry Potter series, where Harry and his friends form the Order of the Phoenix to fight against the rising dark forces.",
                "hp5.jpg",
                "9780747551003",
                "English",
                766,
                15.99,
                LocalDate.of(2024, 9, 5),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Half-Blood Prince",
                "J.K. Rowling",
                "The sixth book in the Harry Potter series, where Harry uncovers the dark past of Lord Voldemort and prepares for the final battle.",
                "hp6.jpg",
                "9780747581086",
                "English",
                607,
                16.99,
                LocalDate.of(2024, 9, 6),
                "Fantasy"
        );

        createBook(
                "Harry Potter and the Deathly Hallows",
                "J.K. Rowling",
                "The final book in the Harry Potter series, where Harry and his friends face their greatest challenge and prepare for the final showdown with Voldemort.",
                "hp7.jpg",
                "9780747591054",
                "English",
                759,
                18.99,
                LocalDate.of(2024, 9, 7),
                "Fantasy"
        );

        createBook(
                "To Kill a Mockingbird",
                "Harper Lee",
                "A novel about racial injustice in the American South, seen through the eyes of a young girl.",
                "mockingbird.jpg",
                "9780060935467",
                "English",
                336,
                9.99,
                LocalDate.of(2024, 9, 1),
                "Classic"
        );

        createBook(
                "1984",
                "George Orwell",
                "A dystopian novel set in a totalitarian society under constant surveillance.",
                "1984.jpg",
                "9780451524935",
                "English",
                328,
                8.99,
                LocalDate.of(2024, 9, 2),
                "Dystopian"
        );

        createBook(
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                "A novel about the mysterious Jay Gatsby and his unrequited love for Daisy Buchanan.",
                "gatsby.jpg",
                "9780743273565",
                "English",
                180,
                9.99,
                LocalDate.of(2024, 9, 3),
                "Classic"
        );

        createBook(
                "Moby-Dick",
                "Herman Melville",
                "The narrative of Captain Ahab's obsessive quest to kill the white whale, Moby Dick.",
                "mobydick.jpg",
                "9780142437247",
                "English",
                635,
                11.99,
                LocalDate.of(2024, 9, 4),
                "Adventure"
        );

        createBook(
                "Pride and Prejudice",
                "Jane Austen",
                "A classic novel that explores the issues of marriage, money, and social status in early 19th century England.",
                "pride.jpg",
                "9781503290563",
                "Others",
                279,
                7.99,
                LocalDate.of(2024, 9, 5),
                "Romance"
        );

        createBook(
                "War and Peace",
                "Leo Tolstoy",
                "A historical novel that chronicles the French invasion of Russia and its impact on Tsarist society.",
                "warpeace.jpg",
                "9780199232765",
                "English",
                1225,
                14.99,
                LocalDate.of(2024, 9, 6),
                "Historical"
        );

        createBook(
                "Brave New World",
                "Aldous Huxley",
                "A dystopian future where society is controlled by technology and social conditioning.",
                "bravenew.jpg",
                "9780060850524",
                "German",
                288,
                10.99,
                LocalDate.of(2024, 9, 7),
                "Science Fiction"
        );

        createBook(
                "The Catcher in the Rye",
                "J.D. Salinger",
                "The story of a teenage boy navigating life and alienation in New York City.",
                "catcher.jpg",
                "9780316769488",
                "English",
                277,
                8.99,
                LocalDate.of(2024, 9, 8),
                "Classic"
        );

        createBook(
                "Crime and Punishment",
                "Fyodor Dostoevsky",
                "A psychological drama that explores morality, religion, and the nature of existence.",
                "crimepunishment.jpg",
                "9780143058144",
                "Others",
                671,
                12.99,
                LocalDate.of(2024, 9, 9),
                "Psychological"
        );

        createBook(
                "Don Quixote",
                "Miguel de Cervantes",
                "The adventures of a nobleman who believes himself to be a knight, and his loyal squire Sancho Panza.",
                "quixote.jpg",
                "9780060934347",
                "German",
                940,
                14.99,
                LocalDate.of(2024, 9, 10),
                "Adventure"
        );
    }

    private void createBook(String title, String author, String description, String imagePath, String isbn,
                            String language, int pages, double price, LocalDate publicationDate, String categoryName) {
        Optional<Category> category = categoryService.findCategoryByName(categoryName);
        if (bookService.getBookByTitle(title).isEmpty() && category.isPresent()) {
            bookService.addBook(new Book(author, title, new BigDecimal(price),  isbn,  publicationDate,
                    pages, language,  imagePath, description, category.get()));
        }
    }
}

