package se.lexicon.libraryapplication.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class BookLoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    LocalDate loanDate;

    @Column(nullable = false)
    LocalDate dueDate;

    @Column(nullable = false)
    boolean returned;

    @ManyToOne
    AppUser borrower;

    @ManyToOne
    Book book;


    public BookLoan() {
    }

    public BookLoan(LocalDate loanDate, LocalDate dueDate, boolean returned, AppUser borrower, Book book) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
        this.borrower = borrower;
        this.book = book;
    }

    public BookLoan(int id, LocalDate loanDate, LocalDate dueDate, boolean returned, AppUser borrower, Book book) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returned = returned;
        this.borrower = borrower;
        this.book = book;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public AppUser getBorrower() {
        return borrower;
    }

    public void setBorrower(AppUser borrower) {
        this.borrower = borrower;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BookLoan{");
        sb.append("id=").append(id);
        sb.append(", loanDate=").append(loanDate);
        sb.append(", dueDate=").append(dueDate);
        sb.append(", returned=").append(returned);
        sb.append(", borrower=").append(borrower);
        sb.append(", book=").append(book);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan bookLoan = (BookLoan) o;
        return id == bookLoan.id && returned == bookLoan.returned && Objects.equals(loanDate, bookLoan.loanDate) && Objects.equals(dueDate, bookLoan.dueDate) && Objects.equals(borrower, bookLoan.borrower) && Objects.equals(book, bookLoan.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loanDate, dueDate, returned, borrower, book);
    }
}
