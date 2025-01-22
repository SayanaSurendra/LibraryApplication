package se.lexicon.libraryapplication.entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 100)
    private String firstName;

    @Column(nullable = false, length = 100)
    private String lastName;


    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private Set<Book> writtenBooks=new HashSet<Book>();



    public Author(String firstName, String lastName) {
        this.lastName = lastName;
        this.firstName = firstName;

    }
    public Author() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Book> getWrittenBooks() {
        return writtenBooks;
    }

    public void setWrittenBooks(Set<Book> writtenBooks) {
        this.writtenBooks = writtenBooks;
    }

    public void addBooks(Book book) {
        writtenBooks.add(book);
        book.getAuthors().add(this);
    }

    public void removeBooks(Book book) {
        writtenBooks.remove(book);
        book.getAuthors().remove(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
      //  sb.append(", writtenBooks=").append(writtenBooks);
        sb.append('}');
        return sb.toString();
    }
}
