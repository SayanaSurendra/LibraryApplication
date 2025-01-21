package se.lexicon.libraryapplication.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.libraryapplication.entity.AppUser;
import se.lexicon.libraryapplication.entity.Book;
import se.lexicon.libraryapplication.entity.BookLoan;
import se.lexicon.libraryapplication.entity.Details;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookLoanRepositoryTest {

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    private BookLoanRepository bookLoanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    Details detailsOfSayana =new Details("sayana@gmail.com","Sayana", LocalDate.of(1988, 1, 1));
    Details detailsOfAnnaS= new Details("anna@gmail.com","Anna", LocalDate.of(1990, 1, 1));

    AppUser appUserSayana=new AppUser("Sayana","1234",LocalDate.of(2024, 05,05),detailsOfSayana);
    AppUser appUserAnnaS=new AppUser("Anna","12345",LocalDate.of(2024, 01,05),detailsOfAnnaS);

    Book b1=new Book("123-345-234-ASB-WTY","Alice in Wonderland",28);
    Book b2=new Book("123-345-234-QWE-AQA","Harry Potter",30);
    Book b3=new Book("123-345-234-DFG-RTY","Shakespeare",30);


    BookLoan bl1 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,02),false,appUserSayana,b1);
    BookLoan bl2 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,05),false,appUserSayana,b2);
    BookLoan bl3 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,01,19),false,appUserAnnaS,b3);

    @BeforeEach
    void setUp() {
        /*detailsRepository.save(detailsOfSayana);
        detailsRepository.save(detailsOfAnnaS);*/
        appUserRepository.save(appUserSayana);
        appUserRepository.save(appUserAnnaS);
       /* bookRepository.save(b1);
        bookRepository.save(b2);
        bookRepository.save(b3);*/
        bookLoanRepository.save(bl1);
        bookLoanRepository.save(bl2);
        bookLoanRepository.save(bl3);

    }

    @Test
    void findByBorrowerId() {
        List<BookLoan> bookLoanList= bookLoanRepository.findByBorrowerId(appUserSayana.getId());
       assertEquals(2,bookLoanList.size());
    }

    @Test
    void findByBookId() {

        List<BookLoan> bookLoanList= bookLoanRepository.findByBookId(b1.getId());
        assertEquals(1,bookLoanList.size());
    }

    @Test
    void findByReturned() {
       List<BookLoan> bl =bookLoanRepository.findBookLoanReturnedIsFalse();
       assertEquals(3,bl.size());
    }

    @Test
    void findByOverDueBookLoans() {
        List<BookLoan> bl =bookLoanRepository.findByOverDueBookLoans(LocalDate.now());
        assertEquals(1,bl.size());
        assertTrue(bl.contains(bl3));


    }

    @Test
    void findBookLoansWithinSpecifiedDates() {
        List<BookLoan> bl = bookLoanRepository.findBookLoansWithinSpecifiedDates(LocalDate.of(2024,12,25) , LocalDate.of(2025,1,6));
        assertEquals(3,bl.size());
        assertTrue(bl.contains(bl3));

    }

    @Test
    void updateBookLoan() {
        assertFalse(bl3.isReturned());
        bookLoanRepository.updateBookLoan(bl3.getId());
        //explicitly call flush() and clear() on the EntityManager to ensure that the changes are written to the database and the cache is cleared.
        entityManager.flush();
        entityManager.clear();
        BookLoan bookLoan=bookLoanRepository.findById(bl3.getId()).get();
         assertTrue(bookLoan.isReturned());
    }


}