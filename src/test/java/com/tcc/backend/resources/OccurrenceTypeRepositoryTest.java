package com.tcc.backend.resources;

import com.tcc.backend.models.OccurrenceType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OccurrenceTypeRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private OccurrenceTypeRepository occurrenceTypeRepository;

    @Test
    public void shouldReturnOccurrenceTypeWhenFindByNameThatExists() {
        String nameOccurrenceType = "name";
        OccurrenceType mockedOccurrenceType = new OccurrenceType();
        mockedOccurrenceType.setName(nameOccurrenceType);
        em.persist(mockedOccurrenceType);

        OccurrenceType occurrenceType = occurrenceTypeRepository.findByName(nameOccurrenceType);

        Assertions.assertNotNull(occurrenceType);
        Assertions.assertEquals(occurrenceType.getName(), nameOccurrenceType);
    }

    @Test
    public void shouldNotReturnOccurrenceTypeWhenFindByNameThatNotExists() {
        String nameOccurrenceType = "name";

        OccurrenceType occurrenceType = occurrenceTypeRepository.findByName(nameOccurrenceType);

        Assertions.assertNull(occurrenceType);
    }
}
