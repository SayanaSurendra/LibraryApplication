package se.lexicon.libraryapplication.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.libraryapplication.entity.Author;
import se.lexicon.libraryapplication.entity.Book;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    Book b1=new Book("123-345-234-ASB-WTY","Alice in Wonderland",28);
    Book b2=new Book("123-345-234-QWE-AQA","Harry Potter",30);
    Book b3=new Book("123-345-234-DFG-RTY","Shakespeare",30);

    Author author1=new Author("Will","Jane");
    Author author2=new Author("JK","Rowling");
    Author author3=new Author("Will","Jane");

    @BeforeEach
    void setUp() {

        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);
        b1.addAuthors(author1);
        b2.addAuthors(author2);
        b3.addAuthors(author3);

        bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);
    }

    @Test
    void findByFirstName() {
        List<Author> al=authorRepository.findByFirstName("Will");
        assertTrue(al.size()==2);
    }

    @Test
    void findByLastName() {
        List<Author> al=authorRepository.findByLastName("Rowling");
        assertTrue(al.size()==1);
        assertEquals(author2,al.get(0));
    }

    @Test
    void findAuthorsByFirstNameOrLastNameContaining() {
        List<Author> al=authorRepository.findAuthorsByFirstNameOrLastNameLikeIgnoreCase("l");
        assertTrue(al.size()==3);
        assertTrue(al.contains(author1));

    }

    @Test
    void findByWrittenBooksId() {
        List<Author> al= authorRepository.findByWrittenBooksId(b1.getId());
        entityManager.flush();
        entityManager.clear();
        assertTrue(al.get(0).getFirstName().equals("Will"));
    }

    @Test
    void updateAuthor() {
        authorRepository.updateAuthor(author1.getId(),"Alice","J");
        entityManager.flush();
        entityManager.clear();
        Author author=authorRepository.findById(author1.getId()).get();
        assertTrue(author.getFirstName().equals("Alice"));

    }

    @Test
    void deleteAuthorById() {
        author1.removeBooks(b1);
        authorRepository.deleteAuthorById(author1.getId());
        entityManager.flush();
        entityManager.clear();
        Optional<Author> deletedAuthor=authorRepository.findById(author1.getId());
        assertTrue(deletedAuthor.isEmpty());

    }
}