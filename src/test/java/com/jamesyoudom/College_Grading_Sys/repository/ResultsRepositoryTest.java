package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import org.junit.jupiter.api.BeforeEach;
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
class ResultsRepositoryTest {


    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    ResultsRepository resultsRepository;

    static Student student;
    static Course course;
    static Teacher teacher;

    @BeforeEach
    public void persistEntities(){
         student = new Student("John", "Doe", "99999999", "abc@gmail.com","Male", "19-10-1914");
         course = new Course("Economics",90.50);
         teacher = new Teacher("Jack", "Richard", "99999999", "abc@gmail.com","Male", "19-10-1914");
         testEntityManager.persist(student);
         testEntityManager.persist(course);
         testEntityManager.persist(teacher);
    }

    @Test
    @DisplayName("Should Return the saved Result by a keyword")
    void findByKeywordTrue() {
        Result result = testEntityManager.persist(new Result(80,90,75,"B", student,course));
        List <Result> results =  resultsRepository.findByKeyword("John");
        assertThat(results.contains(result));
    }

    @Test
    @DisplayName("Should not Return the saved Result by a keyword")
    void findByKeywordFalse() {
        Result result = testEntityManager.persist(new Result(80,90,75,"B",student,course));
        List <Result> results =  resultsRepository.findByKeyword("A");
        assertThat(results.isEmpty());
    }

    @Test
    @DisplayName("Should Return the saved Result by a id")
    void findByStudentIdTrue() {
        Result result = testEntityManager.persist(new Result(80,90,75,"B",student,course));
        List <Result> results = resultsRepository.findByStudentId(student.getStudent_id());
        assertThat(results.contains(result));
    }

    @Test
    @DisplayName("Should not Return the saved Result by a given id")
    void findByStudentIdFalse() {
        Result result = testEntityManager.persist(new Result(80,90,75,"B",student,course));
        List <Result> results = resultsRepository.findByStudentId(Long.valueOf(10));
        assertThat(results.contains(result));
    }

}