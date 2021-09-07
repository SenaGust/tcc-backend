package com.tcc.backend.resources;

import com.tcc.backend.models.User;
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
public class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager em;

    @Test
    public void shouldReturnUserWhenFindByNameThatExists() {
        String nameUser = "name";
        User mockedUser = new User();
        mockedUser.setEmail("test@test.com");
        mockedUser.setName(nameUser);
        em.persist(mockedUser);

        User user = userRepository.findByName(nameUser);


        Assertions.assertNotNull(user);
        Assertions.assertEquals(user.getName(), nameUser);
    }

    @Test
    public void shouldNotReturnUserWhenFindByNameThatNotExists() {
        String nameUser = "name";

        User user = userRepository.findByName(nameUser);

        Assertions.assertNull(user);
    }

}
