package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class StudentRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    StudentRepository studentRepository;

    @Test
    @DisplayName("Should Return the saved Student by a keyword")
    void findByKeywordTrue() {
        Student student = testEntityManager.persist(new Student("John", "Doe", "99999999", "abc@gmail.com","Male", "19-10-1914"));
        List<Student> foundStudent = studentRepository.findByKeyword("Do");
        assertThat(foundStudent.contains(student));
    }

    @Test
    @DisplayName("Should not Return the saved Student by a keyword")
    void findByKeywordFalse() {
        Student student = testEntityManager.persist(new Student("John", "Doe", "99999999", "abc@gmail.com","Male", "19-10-1914"));
        List<Student> foundStudent = studentRepository.findByKeyword("Gf");
        assertThat(foundStudent.isEmpty());
    }
}