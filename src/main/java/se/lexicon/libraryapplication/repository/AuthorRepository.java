package se.lexicon.libraryapplication.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.libraryapplication.entity.Author;

import java.util.List;
import java.util.Set;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

    List<Author> findByFirstName(String firstName);

    List<Author> findByLastName(String lastName);


    @Query("SELECT a FROM Author a WHERE lower(a.firstName) like concat('%',lower(:name),'%') OR lower(a.lastName) like concat('%',lower(:name),'%')")
   List<Author> findAuthorsByFirstNameOrLastNameLikeIgnoreCase(@Param("name")String keyword);


    List<Author> findByWrittenBooksId(Integer id);
    
    @Modifying
    @Transactional
    @Query("update  Author a set a.firstName= :firstName , a.lastName= :lastName where a.id=:id")
    void updateAuthor(Integer id,String firstName, String lastName);

   /* @Modifying
    @Transactional
    @Query("delete from Author a where a.id=:id")
    void deleteAuthor(Integer id);
    */

    @Modifying
    @Transactional
    void deleteAuthorById(Integer id);

}
