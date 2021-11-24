package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import com.jamesyoudom.College_Grading_Sys.repository.CourseRepository;
import com.jamesyoudom.College_Grading_Sys.repository.ResultsRepository;
import com.jamesyoudom.College_Grading_Sys.repository.StudentRepository;
import com.jamesyoudom.College_Grading_Sys.repository.TeacherRepository;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.security.UsersRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TeacherServiceImplTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ResultsRepository resultsRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    static Student student;
    static Course course;
    static Course course2;
    static Teacher teacher;
    static Users user;
    static Result result;
    static Result result2;

    public static String gradeCalculator(int mark1, int mark2, int mark3) {

        int result = (mark1 + mark2 + mark3) / 3;
        String grade = "";
        if (result >= 95) {
            grade = "A+";
        } else if (result >= 90&& result<95) {
            grade = "A";
        } /*else if (result >= 75&& result<90) {
            grade = "B";
        } */else if (result > 60 && result < 75) {
            grade = "C";
        } else if(result<60){
            grade = "Failed";
        }

        return grade;
    }

    @BeforeEach
    public void persistEntities(){
        student = new Student("John", "Doe", "99999999", "abc@gmail.com","Male", "19-10-1914");
        course = new Course("Economics",90.50);
        course2= new Course("Mathematics",100);
        teacher = new Teacher("Jack", "Richard", "99999999", "abc@gmail.com","Male", "19-10-1914");
        user = new Users("ADMIN", "Jack", "Baeur");
        result = testEntityManager.persist(new Result(80,90,75,"B", student,course));
        result2 = testEntityManager.persist(new Result(80,90,75,"B", student,course2));
        testEntityManager.persist(student);
        testEntityManager.persist(course);
        testEntityManager.persist(course2);
        testEntityManager.persist(teacher);
        testEntityManager.persist(user);
        testEntityManager.persist(result);
        testEntityManager.persist(result2);
    }




    @Test
    @DisplayName("Should update the teacher's password")
    void updateTeacherPassword() {
        teacher.setUser(user);
        testEntityManager.persist(teacher);

        Users currentUser = teacher.getUser();

        String username = "Jack";
        String password = "Baeur";
        String newPassword = "Bobo";
        Users userFound = usersRepository.findByusername(username);
        if(!newPassword.equals(password)){
            if(userFound != null && password.equals(userFound.getPassword()) && currentUser.equals(userFound)){
                userFound.setPassword(newPassword);
                testEntityManager.persist(userFound);
            }
        }
        assertThat(userFound.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("Should return a of teacher's students")
    void getTeacherClasses_ListOfStudents() {
        course.setTeacher(teacher);
        testEntityManager.persist(course);

        List<Result> results = resultsRepository.findAll();
        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id())).collect(Collectors.toList());
        List<Student> students = new ArrayList();

        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentRepository.getById(student.getStudent_id());
            students.add(studentTeacher);
        });

        assertThat(students.contains(student));
    }

    @Test
    @DisplayName("Should return teacher's courses")
    void getTeacherCourses() {
        course.setTeacher(teacher);
        testEntityManager.persist(course);
        course2.setTeacher(teacher);
        testEntityManager.persist(course2);

        List<Course> courses = courseRepository.findAll().stream().filter(s -> s.getTeacher().equals(teacher)).collect(Collectors.toList());

        assertThat(courses.contains(course)&&courses.contains(course2));
    }

    @Test
    void getTeacherResults() {
        course.setTeacher(teacher);
        testEntityManager.persist(course);
        course2.setTeacher(teacher);
        testEntityManager.persist(course2);

        List<Result> results = resultsRepository.findAll();
        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id())).collect(Collectors.toList());

        assertThat(teacherResults.contains(student)&&teacherResults.contains(course)&&teacherResults.contains(course2));
    }

    @Test
    @DisplayName("Should return a list of teacher's students for a given course")
    void getTeacherClassesByCourse() {
        course.setTeacher(teacher);
        testEntityManager.persist(course);
        course2.setTeacher(teacher);
        testEntityManager.persist(course2);

        List<Result> results = resultsRepository.findAll();
        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id())).collect(Collectors.toList());
        List<Student> students = new ArrayList();

        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentRepository.getById(student.getStudent_id());
            if (!students.contains(studentTeacher)) {
                students.add(studentTeacher);
            }
        });
        assertThat(students.size()).isEqualTo(1);
        assertThat(students.contains(student));
    }

    @Test
    @DisplayName("Should return a of teacher's students for grades")
    void getTeacherClasses_FoGrades() {
        course.setTeacher(teacher);
        testEntityManager.persist(course);
        course2.setTeacher(teacher);
        testEntityManager.persist(course2);

        List<Result> results = resultsRepository.findAll();
        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id())).collect(Collectors.toList());
        List<Student> students = new ArrayList();

        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentRepository.getById(student.getStudent_id());
            students.add(studentTeacher);
        });

        assertThat(students.contains(student));
        assertThat(students.size()).isEqualTo(2);
    }


    @Test
    @DisplayName("Should save a of teacher's students for a given course")
    void saveStudentGrades() {
        int mark1 = 95;
        int mark2 = 95;
        int mark3 = 98;
        List<Result> results = new ArrayList<>();

        List<Result> studentResults = resultsRepository.findAll().stream().filter(s -> s.getStudent().getStudent_id().equals(student.getStudent_id())).collect(Collectors.toList());
        for (Result rs : studentResults) {

                rs.setMark1(mark1);
                rs.setMark2(mark2);
                rs.setMark3(mark3);

                String grade = gradeCalculator(mark1,mark2,mark3);
                rs.setGrade(grade);
                results.add(rs);
            }

        student.setResults(results);
        testEntityManager.persist(student);
        List<Result> newStudentResults = resultsRepository.findAll().stream().filter(s -> s.getStudent().getStudent_id().equals(student.getStudent_id())).collect(Collectors.toList());

        for(Result result: newStudentResults){
            assertThat(result.getGrade()).isEqualTo("A+");
        }

    }

}