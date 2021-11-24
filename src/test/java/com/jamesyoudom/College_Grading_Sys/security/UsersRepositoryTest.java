package com.jamesyoudom.College_Grading_Sys.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UsersRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    UsersRepository usersRepository;

    @Test
    @DisplayName("Should find the saved User by username")
    void findByUsernameTrue() {
        Users user = testEntityManager.persist(new Users("ADMIN", "Jack", "Baeur"));
        Users foundUser = usersRepository.findByusername("Jack");
        assertThat(user).isEqualTo(foundUser);
    }

    @Test
    @DisplayName("Should not find the saved User by username")
    void findByUsernameFalse() {
        Users user = testEntityManager.persist(new Users("ADMIN", "Jack", "Baeur"));
        Users foundUser = usersRepository.findByusername("Alex");
        assertThat(user).isNotEqualTo(foundUser);
    }

    @Test
    @DisplayName("Should Return the saved Result by a keyword")
    void findByKeywordTrue() {
        Users user = testEntityManager.persist(new Users("ADMIN", "Jack", "Baeur"));
        List<Users> usersList = usersRepository.findByKeyword("Ja");
        assertThat(usersList.contains(user));
    }

    @Test
    @DisplayName("Should not Return the saved Result by a keyword")
    void findByKeywordFalse() {
        Users user = testEntityManager.persist(new Users("ADMIN", "Jack", "Baeur"));
        List<Users> usersList = usersRepository.findByKeyword("Ma");
        assertThat(usersList.contains(user));
    }
}