package se.lexicon.libraryapplication.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String username;

    private String password;
    private LocalDate regDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Details userDetails;


    @OneToMany(mappedBy = "borrower")
    private List<BookLoan> bookLoanList = new ArrayList<>();


    public AppUser() {
    }

    public AppUser(Integer id, String username, String password, LocalDate regDate, Details userDetails) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }

    public AppUser(String username, String password, LocalDate regDate, Details userDetails) {
        this.username = username;
        this.password = password;
        this.regDate = regDate;
        this.userDetails = userDetails;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Details getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(Details userDetails) {
        this.userDetails = userDetails;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public void setRegDate(LocalDate regDate) {
        this.regDate = regDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BookLoan> getBookLoanList() {
        return bookLoanList;
    }

    public void setBookLoanList(List<BookLoan> bookLoanList) {
        this.bookLoanList = bookLoanList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AppUser{");
        sb.append("id=").append(id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", regDate=").append(regDate);
        sb.append(", userDetails=").append(userDetails);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) && Objects.equals(username, appUser.username) && Objects.equals(password, appUser.password) && Objects.equals(regDate, appUser.regDate) && Objects.equals(userDetails, appUser.userDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, regDate, userDetails);
    }


   /* public void borrowBook(BookLoan bookLoan) {

        bookLoan.getBook().setAvailable(false);
        bookLoanList.add(bookLoan);
        bookLoan.setBorrower(this);
    }*/

    public void returnBook(BookLoan bookLoan) {
        bookLoan.setBorrower(null);
        bookLoan.getBook().setAvailable(true);
        bookLoanList.remove(bookLoan);

    }


    public BookLoan borrowBook(Book book, LocalDate borrowDate) {
        if(!book.isAvailable()){
            return null;
        }else{
            LocalDate dueDate=book.calculateDueDate(borrowDate);
            BookLoan bookLoan=new BookLoan(borrowDate,dueDate,false,this,book);
            book.setAvailable(false);
            bookLoanList.add(bookLoan);
            return bookLoan;

        }


    }








}
