package se.lexicon.libraryapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import se.lexicon.libraryapplication.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book,Integer> {

    Book findBookByIsbnIgnoreCase(String isbn);

    Book findByTitleContains(String title);

    List<Book> findBooksByMaxLoanDaysLessThan(int days);
}
