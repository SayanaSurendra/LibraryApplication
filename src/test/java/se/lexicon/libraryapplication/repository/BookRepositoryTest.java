package se.lexicon.libraryapplication.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.libraryapplication.entity.Book;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    Book b1=new Book("123-345-234-ASB-WTY","Alice in Wonderland",28);
    Book b2=new Book("123-345-234-QWE-AQA","Harry Potter",30);



    @BeforeEach
    void setUp() {

        bookRepository.save(b1);
        bookRepository.save(b2);
    }

    @Test
    void findBookByIsbnIgnoreCase() {

        Book book = bookRepository.findBookByIsbnIgnoreCase("123-345-234-ASB-WTY");
        assertNotNull(book);
        assertEquals(b1.getIsbn(),book.getIsbn());
    }

    @Test
    void findByTitleContains() {
        Book book = bookRepository.findByTitleContains("Alice in Wonderland");
        assertNotNull(book);
        assertEquals(b1.getTitle(),book.getTitle());

    }

    @Test
    void findBooksByMaxLoanDaysLessThan() {
        List<Book> books = bookRepository.findBooksByMaxLoanDaysLessThan(40);
        assertNotNull(books);
        assertTrue(books.size() > 0);
        assertEquals(b1.getTitle(),books.get(0).getTitle());
        assertEquals(b2.getTitle(),books.get(1).getTitle());
        assertTrue(books.contains(b2));
    }
}