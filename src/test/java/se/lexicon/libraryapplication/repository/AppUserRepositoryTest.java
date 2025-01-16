package se.lexicon.libraryapplication.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.libraryapplication.entity.AppUser;
import se.lexicon.libraryapplication.entity.Details;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppUserRepositoryTest {

    Details detailsOfSayana =new Details("sayana@gmail.com","Sayana", LocalDate.of(1988, 1, 1));
    Details detailsOfAnnaS= new Details("anna@gmail.com","Anna", LocalDate.of(1990, 1, 1));
    Details detailsOfErik= new Details("erik@gmail.com","Erik", LocalDate.of(1990, 2, 1));
    Details detailsOfAnnaJ= new Details("annaj@gmail.com","Anna", LocalDate.of(1990, 1, 1));
    AppUser appUserSayana=new AppUser("Sayana","1234",LocalDate.of(2024, 05,05),detailsOfSayana);
    AppUser appUserAnnaS=new AppUser("Anna","12345",LocalDate.of(2024, 01,05),detailsOfAnnaS);
    AppUser appUserErik=new AppUser("Erik","1212",LocalDate.of(2024, 07,05),detailsOfErik);
    AppUser appUserAnnaJ=new AppUser("AnnaJ","1234",LocalDate.of(2024, 05,05),detailsOfAnnaJ);

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private DetailsRepository detailsRepository;


    @BeforeEach
    void setUp() {
        detailsRepository.save(detailsOfSayana);
        detailsRepository.save(detailsOfAnnaS);
        detailsRepository.save(detailsOfErik);
        detailsRepository.save(detailsOfAnnaJ);
        repository.save(appUserSayana);
        repository.save(appUserAnnaS);
        repository.save(appUserErik);
        repository.save(appUserAnnaJ);
    }

    @Test
    void findByUsername() {
        AppUser user = repository.findByUsername("Erik");
        assertNotNull(user);
        assertEquals(appUserErik,user);
    }

    @Test
    void findByRegDateBetween() {
        List<AppUser> appUsers=repository.findByRegDateBetween(LocalDate.of(2024, 01, 01), LocalDate.of(2024, 05, 01));
        assertNotNull(appUsers);
        assertEquals(1,appUsers.size());
        assertEquals(appUserAnnaS,appUsers.get(0));
    }

    @Test
    void findByDetailsId() {
       AppUser user = repository.findByDetailsId(detailsOfSayana.getId());
        assertNotNull(user);
        assertEquals(appUserSayana,user);
    }

    @Test
    void findByEmail() {
       AppUser user = repository.findByEmail("anna@gmail.com");
       assertNotNull(user);
       assertEquals(appUserAnnaS,user);
    }
}