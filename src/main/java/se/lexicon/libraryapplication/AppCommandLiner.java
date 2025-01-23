package se.lexicon.libraryapplication;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.libraryapplication.entity.*;
import se.lexicon.libraryapplication.repository.*;

import java.time.LocalDate;
import java.util.List;

@Component
public class AppCommandLiner implements CommandLineRunner {

    private DetailsRepository detailsRepository;

    private AppUserRepository appUserRepository;

    private BookRepository bookRepository;

    private BookLoanRepository bookLoanRepository;

    private AuthorRepository authorRepository;

   @Autowired
    public AppCommandLiner(DetailsRepository detailsRepository, AppUserRepository appUserRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository, AuthorRepository authorRepository) {
        this.detailsRepository = detailsRepository;
        this.appUserRepository = appUserRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
        this.authorRepository=authorRepository;

    }

    @Override
    public void run(String... args) throws Exception {

        Details detailsOfSayana =new Details("sayana@gmail.com","Sayana", LocalDate.of(1988, 1, 1));
        Details detailsOfAnnaS= new Details("anna@gmail.com","Anna", LocalDate.of(1990, 1, 1));
        Details detailsOfErik= new Details("erik@gmail.com","Erik", LocalDate.of(1990, 2, 1));
        Details detailsOfAnnaJ= new Details("annaj@gmail.com","Anna", LocalDate.of(1990, 2, 1));

        AppUser appUserSayana=new AppUser("Sayana","1234",LocalDate.of(2024, 05,05),detailsOfSayana);
        AppUser appUserAnnaS=new AppUser("Anna","12345",LocalDate.of(2024, 01,05),detailsOfAnnaS);
        AppUser appUserErik=new AppUser("Erik","1212",LocalDate.of(2024, 07,05),detailsOfErik);
        AppUser appUserAnnaJ=new AppUser("AnnaJ","1234",LocalDate.of(2024, 05,05),detailsOfAnnaJ);

         // Storing users to DB
        appUserRepository.save(appUserSayana);
        appUserRepository.save(appUserAnnaS);
        appUserRepository.save(appUserErik);
        appUserRepository.save(appUserAnnaJ);

        Book b1=new Book("123-345-234-ASB-WTY","Alice in Wonderland",28);
        Book b2=new Book("123-345-234-QWE-AQA","Harry Potter",30);
        Book b3=new Book("123-345-234-DFG-RTY","Shakespeare",30);

        Author author1=new Author("Will","Jane");
        Author author2=new Author("JK","Rowling");
        Author author3=new Author("Alice","Jane");

        // Storing authors to DB
        authorRepository.save(author1);
        authorRepository.save(author2);
        authorRepository.save(author3);

        b1.addAuthors(author1);
        b2.addAuthors(author2);
        b2.addAuthors(author3);
        b3.addAuthors(author3);

        // Stroing books to DB
       bookRepository.save(b1);
       bookRepository.save(b2);
       bookRepository.save(b3);

       /* BookLoan bl1 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,02),false,appUserSayana,b1);
        BookLoan bl2 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,05),false,appUserSayana,b2);
        BookLoan bl3 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,01,19),false,appUserAnnaS,b3);*/
       // BookLoan bl4=new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,01,19),false,appUserErik,b3);

        /*bookLoanRepository.save(bl1);
        bookLoanRepository.save(bl2);
        bookLoanRepository.save(bl3);*/


       BookLoan bl1= bookLoanProcess(appUserErik,b2,LocalDate.of(2025,01,23));
       BookLoan bl2=bookLoanProcess(appUserAnnaS,b1,LocalDate.of(2025,01,23));
       BookLoan bl3=bookLoanProcess(appUserAnnaJ,b3,LocalDate.of(2025,01,23));


       // Book cannot be loaned as it is not available
       BookLoan blerror= bookLoanProcess(appUserAnnaJ,b2,LocalDate.of(2025,01,23));

       //returning a book
        bookReturnProcess(appUserErik,bl1);

        //another user trying to borrow the book which has been returned  as per above line
        BookLoan bl4= bookLoanProcess(appUserAnnaJ,b2,LocalDate.of(2025,01,23));


        // Removing author from book, delete authorById

       author3.removeBooks(b2);
        author3.removeBooks(b3);
        b2.removeAuthors(author3);
        b3.removeAuthors(author3);

        bookRepository.save(b2);
        bookRepository.save(b3);
        authorRepository.save(author3);
        authorRepository.deleteAuthorById(author3.getId());





    }

    private void bookReturnProcess(AppUser user,BookLoan bl) {
       user.returnBook(bl);
       bookRepository.save(bl.getBook());
       bookLoanRepository.markBookLoanAsReturned(bl.getId());
        System.out.println("Book returned successfully");
    }

    private BookLoan bookLoanProcess(AppUser user, Book b, LocalDate date) {
        BookLoan bookLoan = user.borrowBook(b, date);
        if (bookLoan != null) {
            bookRepository.save(b);
            bookLoanRepository.save(bookLoan);
            System.out.println("Book loaned successfyully");

        } else {
            System.out.println("Book  is not available ");

        }
        return bookLoan;
    }



}
