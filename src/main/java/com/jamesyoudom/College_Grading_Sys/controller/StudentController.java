/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.controller;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.service.CourseService;
import com.jamesyoudom.College_Grading_Sys.service.EmailSenderService;
import com.jamesyoudom.College_Grading_Sys.service.StudentService;
import com.jamesyoudom.College_Grading_Sys.service.TeacherService;
import com.jamesyoudom.College_Grading_Sys.service.UsersService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author youdo
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    /* Students' Service*/
    @Autowired
    StudentService studentService;

    /* Courses' Service*/
    @Autowired
    CourseService courseService;

    /* Teachers' Service*/
    @Autowired
    TeacherService teacherService;


    /* Users' Service*/
    @Autowired
    UsersService usersService;

    /*Mail Sender Service*/
    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/home")
    public String getIndex(Model model) {
        Users user = getCurrentUser();
        model.addAttribute("user", user);
        return "students/student_index";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Users user = getCurrentUser();
        Student student = user.getStudent();
        model.addAttribute("student", student);
        return "students/profile";
    }

    @GetMapping("/profile/update")
    public String updateProfile(Model model) {
        Users user = getCurrentUser();
        Student student = user.getStudent();
        model.addAttribute("student", student);
        return "students/profile_update";
    }

    @PostMapping("/profile/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model, HttpStatus http) {
        studentService.updateStudent(student, id);
        model.addAttribute("student", student);
        return "redirect:";
    }

    @GetMapping("/password/update")
    public String updatePassword(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "students/passwordUpdate";
    }

    @PostMapping("/password/update")
    public String updatePassword(@ModelAttribute("user") Users user, Model model) {
        Users currentUser = getCurrentUser();       
        Users user1 = new Users();
        model.addAttribute("user", user1);
        model.addAttribute("user", user);
        model.addAttribute("message", studentService.updateStudentPassword(user, currentUser));
        return "students/passwordUpdate";
    }

    @GetMapping("/mycourses")
    public String getStudentCourses(Model model) {
        Student currentStudent = getCurrentUser().getStudent();
        List<Result> results = currentStudent.getResults();
        model.addAttribute("message", studentService.getStudentCourses_message(results));
        model.addAttribute("results", results);
        return "students/myCourses";
    }

    @GetMapping("/results")
    public String getStudentResults(Model model) {
        Student student = getCurrentUser().getStudent();
        List<Result> results = student.getResults();
        model.addAttribute("message", studentService.getStudentResults(results));
        model.addAttribute("student", student);
        model.addAttribute("results", results);
        return "students/results";
    }

    @GetMapping("/assignments")
    public String getStudentAssignments(Model model) {
        Student student = getCurrentUser().getStudent();
        List<Result> results = student.getResults();
        List<Course> coursesWithAssignments = studentService.getStudentAssignments(results);
        if (!coursesWithAssignments.isEmpty()) {
            model.addAttribute("courses", coursesWithAssignments);           
        } else {
            String message = "There is no assignment for the moment.";
            model.addAttribute("message", message);
        }        
        return "students/assignments_CourseSelection";
    }

    @GetMapping("/assignments/{id}")
    public String getStudentAssignmentCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "students/assignments";
    }

    @PostMapping("/assignments/{id}")
    public String sendStudentAssignments(@PathVariable Long id, @RequestParam("file") MultipartFile file, Model model) throws IOException, MessagingException {
        // Send email to teacher with the file
        Student student = getCurrentUser().getStudent();
        model.addAttribute("message", studentService.sendStudentAssignment(student, id, file));
        return "students/assignments_success";
    }

    public Users getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return usersService.findByUsersname(username);
        } else {
            String username = principal.toString();
            return usersService.findByUsersname(username);
        }

    }

}
