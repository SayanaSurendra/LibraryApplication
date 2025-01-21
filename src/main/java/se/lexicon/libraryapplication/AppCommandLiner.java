package se.lexicon.libraryapplication;

import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import se.lexicon.libraryapplication.entity.AppUser;
import se.lexicon.libraryapplication.entity.Book;
import se.lexicon.libraryapplication.entity.BookLoan;
import se.lexicon.libraryapplication.entity.Details;
import se.lexicon.libraryapplication.repository.AppUserRepository;
import se.lexicon.libraryapplication.repository.BookLoanRepository;
import se.lexicon.libraryapplication.repository.BookRepository;
import se.lexicon.libraryapplication.repository.DetailsRepository;

import java.time.LocalDate;

@Component
public class AppCommandLiner implements CommandLineRunner {

    private DetailsRepository detailsRepository;

    private AppUserRepository appUserRepository;

    private BookRepository bookRepository;

    private BookLoanRepository bookLoanRepository;

   @Autowired
    public AppCommandLiner(DetailsRepository detailsRepository, AppUserRepository appUserRepository, BookRepository bookRepository, BookLoanRepository bookLoanRepository) {
        this.detailsRepository = detailsRepository;
        this.appUserRepository = appUserRepository;
        this.bookRepository = bookRepository;
        this.bookLoanRepository = bookLoanRepository;
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


        appUserRepository.save(appUserSayana);
        appUserRepository.save(appUserAnnaS);
        appUserRepository.save(appUserErik);
        appUserRepository.save(appUserAnnaJ);

        Book b1=new Book("123-345-234-ASB-WTY","Alice in Wonderland",28);
        Book b2=new Book("123-345-234-QWE-AQA","Harry Potter",30);
        Book b3=new Book("123-345-234-DFG-RTY","Shakespeare",30);


        BookLoan bl1 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,02),false,appUserSayana,b1);
        BookLoan bl2 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,02,05),false,appUserSayana,b2);
        BookLoan bl3 =new BookLoan(LocalDate.of(2025,01,05),LocalDate.of(2025,01,19),false,appUserAnnaS,b3);


        bookLoanRepository.save(bl1);
        bookLoanRepository.save(bl2);
        bookLoanRepository.save(bl3);

        bookLoanRepository.updateBookLoan(bl3.getId());
    }
}
