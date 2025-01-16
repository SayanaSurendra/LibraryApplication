package se.lexicon.libraryapplication.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.lexicon.libraryapplication.entity.Details;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetailsRepository extends CrudRepository<Details, Integer> {

     Details findByEmail(String email);
     List<Details> findByNameContaining(String name);
     List<Details> findByNameContainingIgnoreCase(String name);
     List<Details> findDetailsByBirthDate(LocalDate birthDate);



}
