package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TeacherRepository teacherRepository;

    @Test
    @DisplayName("Should Return the saved Teacher by a keyword")
    void findByKeywordTrue() {
    Teacher teacher = testEntityManager.persist(new Teacher("Jack", "Richard", "99999999", "abc@gmail.com","Male", "19-10-1914"));
    List<Teacher> foundTeachers = teacherRepository.findByKeyword("Ja");
        assertThat(foundTeachers.contains(teacher));
    }

    @Test
    @DisplayName("Should not Return the saved Teacher by a keyword")
    void findByKeywordFalse() {
        Teacher teacher = testEntityManager.persist(new Teacher("Jack", "Richard", "99999999", "abc@gmail.com","Male", "19-10-1914"));
        List<Teacher> foundTeachers = teacherRepository.findByKeyword("Pa");
        assertThat(foundTeachers.isEmpty());
    }
}