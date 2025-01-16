package se.lexicon.libraryapplication.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.libraryapplication.entity.Details;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DetailsRepositoryTest {

    Details detailsOfSayana =new Details("sayana@gmail.com","Sayana", LocalDate.of(1988, 1, 1));
    Details detailsOfAnnaS= new Details("anna@gmail.com","Anna", LocalDate.of(1990, 1, 1));
    Details detailsOfErik= new Details("erik@gmail.com","Erik", LocalDate.of(1990, 2, 1));
    Details detailsOfAnnaJ= new Details("annaj@gmail.com","Anna", LocalDate.of(1990, 2, 1));


    @Autowired
    private DetailsRepository detailsRepository;

    @BeforeEach
    void setUp() {

        detailsRepository.save(detailsOfSayana);
        detailsRepository.save(detailsOfAnnaS);
        detailsRepository.save(detailsOfErik);
        detailsRepository.save(detailsOfAnnaJ);

    }

    @Test
    void findByEmail() {
       Details detail=detailsRepository.findByEmail("sayana@gmail.com");
       assertNotNull(detail);
       assertEquals("sayana@gmail.com",detail.getEmail());

    }

    @Test
    void findByNameContaining() {

        List<Details> detailsList=detailsRepository.findByNameContaining("Sayana");

        assertNotNull(detailsList);
        assertEquals(1, detailsList.size());
        assertTrue(detailsList.contains(detailsOfSayana));


    }



    @Test
    void findByNameContainingIgnoreCase() {
        List<Details> detailsList=detailsRepository.findByNameContainingIgnoreCase("anna");
        assertNotNull(detailsList);
        assertEquals(2, detailsList.size());
        assertTrue(detailsList.stream().anyMatch(s -> s.getName().equalsIgnoreCase("anna")));

    }

    @Test
    void findDetailsByBirthDate() {
        List<Details> detailsList = detailsRepository.findDetailsByBirthDate(LocalDate.of(1990,2,1));
        assertTrue(detailsList.contains(detailsOfAnnaJ));
    }
}