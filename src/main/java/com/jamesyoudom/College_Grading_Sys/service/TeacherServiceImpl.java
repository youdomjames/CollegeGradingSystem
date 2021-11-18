/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import com.jamesyoudom.College_Grading_Sys.repository.TeacherRepository;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author youdo
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    UsersService usersService;

    @Autowired
    ResultService resultService;

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Override
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher saveTeacher(Teacher teacher) {
        String fullName = teacher.getFirstName() + " " + teacher.getLastName();
        teacher.setFullName(fullName);
        String fullAddress = teacher.getAddress_StreetName() + ", " + teacher.getCity() + " (" + teacher.getProvince() + ") " + " - " + teacher.getCountry();
        teacher.setFullAddress(fullAddress);

        Users user = new Users();
        user.setFirstName(teacher.getFirstName());
        user.setLastName(teacher.getLastName());

        //Generating random username 
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String username = teacher.getFirstName() + random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        //Generating random password 
        int leftLimit2 = 48; // numeral '0'
        int rightLimit2 = 122; // letter 'z'
        int targetStringLength2 = 10;
        Random random2 = new Random();

        String password = random2.ints(leftLimit2, rightLimit2 + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength2)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        user.setUsername(username);
        user.setPassword(password);

        //Adding a role to user
        user.setRole("TEACHER");
        teacher.setUser(user);
        return teacherRepository.save(teacher);
    }

    @Override
    public Teacher getTeacherById(Long id) {
        return teacherRepository.getById(id);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher, Long id) {
        // get teacher from database by id
        Teacher existingTeacher = getTeacherById(id);
        existingTeacher.setTeacher_id(id);
        existingTeacher.setFirstName(teacher.getFirstName());
        existingTeacher.setAddress_StreetName(teacher.getAddress_StreetName());
        existingTeacher.setCity(teacher.getCity());
        existingTeacher.setProvince(teacher.getProvince());
        existingTeacher.setCountry(teacher.getCountry());
        existingTeacher.setPostalCode(teacher.getPostalCode());
        existingTeacher.setEmail(teacher.getEmail());
        existingTeacher.setPhoneNumber(teacher.getPhoneNumber());
        existingTeacher.setFax(teacher.getFax());
        existingTeacher.setGender(teacher.getGender());
        existingTeacher.setDateOfBirth(teacher.getDateOfBirth());

        //updating user's info
        Users updatedUser = teacher.getUser();
        updatedUser.setFirstName(teacher.getFirstName());
        updatedUser.setLastName(teacher.getLastName());
        existingTeacher.setUser(updatedUser);

        return teacherRepository.save(existingTeacher);
    }

    @Override
    public List<Teacher> getTeacherByKeyword(String keyword) {
        return teacherRepository.findByKeyword(keyword);
    }

    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher simpleSave(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    /**
     * ******CUSTOM METHODS********
     */
    @Override
    public String updateTeacherPassword(Users user, Users currentUser) {

        Users userFound = usersService.findByUsersname(user.getUsername());

        if (!user.getNewPassword().equals(user.getPassword())) {
            if (userFound != null && user.getPassword().equals(userFound.getPassword()) && currentUser.equals(userFound)) {
                userFound.setPassword(user.getNewPassword());
                usersService.saveUsers(userFound);
                String msg = "Password changed successfully";
                return msg;
            } else if (userFound == null) {
                String msg = "Wrong Username, please try again";
                return msg;
            } else if (!userFound.equals(currentUser)) {
                String msg = "You don't have access to this user, please try again";
                return msg;
            } else if (!user.getPassword().equals(userFound.getPassword())) {
                String msg = "Wrong Password, please try again";
                return msg;
            } else {
                String msg = "Something went wrong please try again";
                return msg;
            }
        } else {
            String msg = "Password not differend from the old one, please choose a new password";
            return msg;
        }
    }

    @Override
    public List<Student> getTeacherClasses_ListOfStudents(List<Result> teacherResults) {
        List<Student> students = new ArrayList();
        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentService.getStudentById(student.getStudent_id());
            students.add(studentTeacher);
        });

        return students;
    }

    @Override
    public List<Course> getTeacherAssignments(Teacher teacher) {

        List<Course> courses = courseService.getAllCourses().stream().filter(s -> s.getTeacher().equals(teacher)).collect(Collectors.toList());

        return courses;
    }

    @Override
    public List<Result> getTeacherResults(Teacher teacher) {

        List<Result> results = resultService.getAllResults();

        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id())).collect(Collectors.toList());

        return teacherResults;
    }

    @Override
    public List<Student> getTeacherClassesByCourse(Teacher teacher, Course course) {

        List<Result> results = resultService.getAllResults();
        List<Result> teacherResults = results.stream().filter(s -> s.getCourse().getTeacher().getTeacher_id().equals(teacher.getTeacher_id()) && s.getCourse().getCourse_id().equals(course.getCourse_id())).collect(Collectors.toList());

        List<Student> students = new ArrayList();
        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentService.getStudentById(student.getStudent_id());
            if (!students.contains(studentTeacher)) {
                students.add(studentTeacher);
            }
        });
        return students;
    }

    @Override
    public List<Student> getTeacherClasses_FoGrades(List<Result> teacherResults) {
        List<Student> students = new ArrayList();
        teacherResults.stream().map(result -> result.getStudent()).forEachOrdered((Student student) -> {
            Student studentTeacher = studentService.getStudentById(student.getStudent_id());
            if (!students.contains(studentTeacher)) {
                students.add(studentTeacher);
            }

        });

        return students;
    }

    @Override
    public List<Course> getTeacherCourses(List<Result> teacherResults) {
        List<Course> courses = new ArrayList();
        teacherResults.stream().map(result -> result.getCourse()).forEachOrdered((Course course) -> {
            Course teacherCourse = courseService.getCourseById(course.getCourse_id());
            if (!courses.contains(teacherCourse)) {
                courses.add(teacherCourse);
            }

        });

        return courses;
    }

    @Override
    public String saveStudentGrades(Result result, Student student) {

        List<Result> studentResults = student.getResults().stream().filter(s -> s.getStudent().getStudent_id().equals(student.getStudent_id())).collect(Collectors.toList());
        for (Result rs : studentResults) {
            if (rs.getGrade() == null) {
                rs.setMark1(result.getMark1());
                rs.setMark2(result.getMark2());
                rs.setMark3(result.getMark3());
                String grade = gradeCalculator(rs.getMark1(), rs.getMark2(), rs.getMark2());
                rs.setGrade(grade);
                student.getResults().add(rs);
                studentService.saveStudent(student);

                String message = "You have successfully provided grades for " + student.getFullName();
                return message;
            }        
        }
        String message = "Grades already inserted, expect no changes!";
        return message;
    }

    public String gradeCalculator(int mark1, int mark2, int mark3) {

        int result = (mark1 + mark2 + mark3) / 3;
        String grade = "";
        if (result >= 95) {
            grade = "A+";
        } else if (result >= 90) {
            grade = "A";
        } else if (result >= 75) {
            grade = "B";
        } else if (result > 60 && result < 75) {
            grade = "C";
        } else if(result<60){
            grade = "Failed";
        }

        return grade;
    }
}
