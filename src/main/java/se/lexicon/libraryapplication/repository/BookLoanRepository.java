package se.lexicon.libraryapplication.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import se.lexicon.libraryapplication.entity.BookLoan;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookLoanRepository extends CrudRepository<BookLoan, Integer> {

    List<BookLoan> findByBorrowerId(int borrowerId);
    
    List<BookLoan> findByBookId(Integer bookId);

    //List<BookLoan> findByReturned(boolean returned);
   @Query("select b from BookLoan b where  b.returned is false ")
   List<BookLoan> findBookLoanReturnedIsFalse();


    @Query("select b from BookLoan b where b.dueDate < :currentDate and b.returned is false ")
   List<BookLoan> findByOverDueBookLoans(LocalDate currentDate);


    @Query("select b from BookLoan b where b.loanDate  between :startDate and :endDate")
    List<BookLoan> findBookLoansWithinSpecifiedDates(LocalDate startDate, LocalDate endDate);

    @Modifying
    @Query("update BookLoan b set b.returned= true where b.id= :loanId")
    void updateBookLoan(@Param("loanId") Integer id);



}
