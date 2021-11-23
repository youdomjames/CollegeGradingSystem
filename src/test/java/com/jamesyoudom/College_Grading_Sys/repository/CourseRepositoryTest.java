package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CourseRepositoryTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CourseRepository courseRepository;

    @Test
    @DisplayName("Should Return the saved Course by a keyword")
    void findByKeywordTrue() {
        Course course = testEntityManager.persist(new Course("Economics", 90.0));
        List<Course> foundCourses = courseRepository.findByKeyword("Eco");
        assertThat(foundCourses.contains(course));
    }

    @Test
    @DisplayName("Should not Return the saved Course by a keyword")
    void findByKeywordFalse() {
        Course course = testEntityManager.persist(new Course("Economics", 90.0));
        List<Course> foundCourses = courseRepository.findByKeyword("Ma");
        assertThat(foundCourses.isEmpty());
    }
}