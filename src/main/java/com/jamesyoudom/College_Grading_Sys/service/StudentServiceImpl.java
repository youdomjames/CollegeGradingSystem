/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.repository.StudentRepository;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author youdo
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    ResultService resultService;

    @Autowired
    UsersService usersService;

    @Autowired
    EmailSenderService emailSenderService;

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {

        List<Student> students = studentRepository.findAll();

        if (!students.contains(student)) {
            String fullAddress = student.getAddress_StreetName() + ", " + student.getCity() + " " + "(" + student.getProvince() + ")" + ", " + student.getPostalCode() + " - " + student.getCountry();
            student.setFullAddress(fullAddress);
            String fullName = student.getFirstName() + " " + student.getLastName();
            student.setFullName(fullName);
            generateUser(student);
        }

        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public Student updateStudent(Student student, Long id) {
        // get student from database by id..
        Student existingStudent = getStudentById(id);
        existingStudent.setStudent_id(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setBadgeName(student.getBadgeName());
        existingStudent.setOrganisation(student.getOrganisation());
        existingStudent.setAddress_StreetName(student.getAddress_StreetName());
        existingStudent.setCity(student.getCity());
        existingStudent.setProvince(student.getProvince());
        existingStudent.setCountry(student.getCountry());
        existingStudent.setPostalCode(student.getPostalCode());
        existingStudent.setEmail(student.getEmail());
        existingStudent.setPhoneNumber(student.getPhoneNumber());
        existingStudent.setFax(student.getFax());
        existingStudent.setGender(student.getGender());
        existingStudent.setDateOfBirth(student.getDateOfBirth());

        //updating user's info
        Users updatedUser = existingStudent.getUser();
        updatedUser.setFirstName(existingStudent.getFirstName());
        updatedUser.setLastName(existingStudent.getLastName());
        existingStudent.setUser(updatedUser);

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> getStudentByKeyword(String keyword) {
        return studentRepository.findByKeyword(keyword);
    }

    @Override
    public Student simpleSave(Student student) {

        return studentRepository.save(student);
    }

    /**
     * *******CUSTOM METHODS**********
     */
    @Override
    public String addStudentsCourse(Long courseId, Long id) {
        Course course = courseService.getCourseById(courseId);
        Student existingStudent = getStudentById(id);
        List<Result> existing_Student_Results = resultService.findByStudentId(id);
        List<Result> existingStudentCourse = existing_Student_Results.stream().filter(s -> s.getCourse().equals(course)).collect(Collectors.toList());

        if (existingStudentCourse.size() >= 1) {
            return "Course already present";
        } else {
            double newTuitionFees = existingStudent.getTuitionFees() + course.getPrice();
            existingStudent.setTuitionFees(newTuitionFees);

            Result resultSemester = new Result();
            resultSemester.setStudent(existingStudent);
            resultSemester.setCourse(course);
            resultService.saveResult(resultSemester);
            List<Result> student_results = resultService.findByStudentId(id);
            existingStudent.setResults(student_results);

            simpleSave(existingStudent);
            return null;
        }
    }

    @Override
    public String updateStudentPassword(Users user, Users currentUser) {

        Users userFound = usersService.findByUsersname(user.getUsername());

        if (!user.getNewPassword().equals(user.getPassword())) {
            if (userFound != null && user.getPassword().equals(userFound.getPassword()) && currentUser.equals(userFound)) {
                userFound.setPassword(user.getNewPassword());
                usersService.saveUsers(userFound);
                return "Password changed successfully";
            } else if (userFound == null) {
                return "Wrong Username, please try again";
            } else if (!userFound.equals(currentUser)) {
                return "You don't have access to this user, please try again";
            } else if (!user.getPassword().equals(userFound.getPassword())) {
                return "Wrong Password, please try again";
            } else {
                return "Something went wrong please try again";
            }
        } else {
            return "Password not differend from the old one, please choose a new password";
        }
    }

    @Override
    public String getStudentCourses_message(List<Result> results) {
        if (results.isEmpty()) {
            return "You have no course for the moment, please comeback later";
        } else {
            return null;
        }
    }

    @Override
    public String getStudentResults(List<Result> results) {
        if (results.isEmpty()) {
            return "You have no course for the moment, please comeback later";
        } else {
            return null;
        }
    }

    @Override
    public List<Course> getStudentCourses(List<Result> results) {
        List<Course> courses = new ArrayList();
        results.stream().map(result -> result.getCourse()).forEachOrdered((Course course) -> {
            Course studentCourse = courseService.getCourseById(course.getCourse_id());
            courses.add(studentCourse);
        });

        return courses;
    }

    @Override
    public List<Course> getStudentAssignments(List<Result> results) {
        List<Course> coursesWithAssignments = new ArrayList();
        for (Result result : results) {
            String ass_Note = result.getCourse().getAssignment_Note();
            if (ass_Note != null) {
                coursesWithAssignments.add(result.getCourse());
            }
        }
        return coursesWithAssignments;
    }

    @Override
    public String sendStudentAssignment(Student student, Long id, MultipartFile file) throws MessagingException, UnsupportedEncodingException {
        Course courseFound = courseService.getCourseById(id);
        String teacherEmail = courseFound.getTeacher().getEmail();

        emailSenderService.sendEmailToTeacher(teacherEmail, courseFound, student, file);

        return "Assignment file successfully sent!";
    }

    @Override
    public List<Student> finalGradeCalculation() {
        List<Student> students = getAllStudents();

        for (Student student : students) {
            List<Result> results = student.getResults();
            if (!results.isEmpty()) {
                double partialTotal = 0;
                for (Result result : results) {
                    double courseAverage = (result.getMark1() + result.getMark2() + result.getMark3()) / 3;
                     partialTotal = partialTotal + courseAverage;
                    System.out.println("************PartialTotal: "+partialTotal);
                }
                double average = partialTotal / results.size();
System.out.println("************Average: "+average);
                String finalGrade = "";
                if (average >= 90) {
                    finalGrade = "A";
                    student.setFinal_Grade(finalGrade);
                    simpleSave(student);
                } else if (average >= 75) {
                    finalGrade = "B";
                    student.setFinal_Grade(finalGrade);
                    simpleSave(student);
                } else if (average >= 60) {
                    finalGrade = "C";
                    student.setFinal_Grade(finalGrade);
                    simpleSave(student);
                } else if(average<60){
                    finalGrade = "Failed";
                    student.setFinal_Grade(finalGrade);
                    simpleSave(student);
                }
            }
        }

        return students;
    }

    public void generateUser(Student student) {

        Users user = new Users();
        user.setFirstName(student.getFirstName());
        user.setLastName(student.getLastName());

        //Generating random username 
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();

        String username = student.getFirstName() + random.ints(leftLimit, rightLimit + 1)
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
        user.setRole("STUDENT");
        user.setStudent(student);
        student.setUser(user);
    }

}
