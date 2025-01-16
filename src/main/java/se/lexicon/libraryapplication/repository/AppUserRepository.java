package se.lexicon.libraryapplication.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.lexicon.libraryapplication.entity.AppUser;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Integer> {

    AppUser findByUsername(String username);

    List<AppUser> findByRegDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("select u from  AppUser u  where u.userDetails.id= :id ")
    AppUser findByDetailsId(@Param("id")Integer id);


    @Query("select u from  AppUser u  where u.userDetails.email= :email ")
    AppUser findByEmail(@Param("email") String email);



}
