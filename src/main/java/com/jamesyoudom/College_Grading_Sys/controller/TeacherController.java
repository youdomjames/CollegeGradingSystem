/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.controller;

import com.jamesyoudom.College_Grading_Sys.model.*;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.service.*;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

/**
 *
 * @author youdo
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {

    /* Students Service*/
    @Autowired
    StudentService studentService;

    /* Courses Service*/
    @Autowired
    CourseService courseService;

    /* Teachers Service*/
    @Autowired
    TeacherService teacherService;

    /* Users Service*/
    @Autowired
    UsersService usersService;

    /* Result Service*/
    @Autowired
    ResultService resultService;

    /* Email Service*/
    @Autowired
    EmailSenderService emailSenderService;

    @GetMapping("/home")
    public String getIndex(Model model) {
        Users user = getCurrentUser();
        model.addAttribute("user", user);
        return "teachers/teacher_index";
    }

    @GetMapping("/profile")
    public String getProfile(Model model) {
        Users user = getCurrentUser();
        Teacher teacher = user.getTeacher();
        model.addAttribute("teacher", teacher);
        return "teachers/profile";
    }

    @GetMapping("/profile/update")
    public String updateProfile(Model model) {
        Users user = getCurrentUser();
        Teacher teacher = user.getTeacher();
        model.addAttribute("teacher", teacher);
        return "teachers/profile_update";
    }

    @PostMapping("/profile/{id}")
    public String updateTeacher(@PathVariable Long id, @ModelAttribute("teacher") Teacher teacher, Model model) {
        teacherService.updateTeacher(teacher, id);
        String message = "Your profile has been successfully updated";
        model.addAttribute("message", message);
        model.addAttribute("teacher", teacher);
        return "redirect:";
    }

    @GetMapping("/password/update")
    public String updatePassword(Model model) {
        Users user = new Users();
        model.addAttribute("user", user);
        return "teachers/passwordUpdate";
    }

    @PostMapping("/password/update")
    public String updatePassword(@ModelAttribute("user") Users user, Model model) {
        Users currentUser = getCurrentUser();
        Users user1 = new Users();
        model.addAttribute("user", user1);
        model.addAttribute("message", teacherService.updateTeacherPassword(user, currentUser));
        model.addAttribute("user", user);
        return "teachers/passwordUpdate";
    }

    @GetMapping("/myclasses")
    public String getTeacherClasses(Model model) {
        Teacher teacher = getCurrentUser().getTeacher();
        List<Result> teacherResults = teacherService.getTeacherResults(teacher);
        List<Student> students = teacherService.getTeacherClasses_ListOfStudents(teacherResults);
        int size = students.size();

        if (teacherResults.isEmpty()) {
            String message = "You have no class for the moment, please comeback later";
            model.addAttribute("message", message);
        }
        model.addAttribute("teacher", teacher);
        model.addAttribute("results", teacherResults);
        model.addAttribute("size", size);
        return "teachers/myclasses";
    }

    @GetMapping("/assignments")
    public String getTeacherAssignments(Model model) {

        Course course = new Course();
        Teacher teacher = getCurrentUser().getTeacher();
        List<Course> courses = teacherService.getTeacherCourses(teacher);

        if (courses.isEmpty()) {
            String message = "You have no course for the moment, please comeback later";
            model.addAttribute("message", message);
        }
        model.addAttribute("course", course);
        model.addAttribute("courses", courses);
        return "teachers/assignments";
    }

    @GetMapping("/assignments/{id}")
    public String getTeacherAssignmentsByCourse(@PathVariable Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        return "teachers/assignments_course";
    }

    @PostMapping("/assignments/{id}")
    public String saveTeacherAssignments(@PathVariable Long id, @RequestParam("file") MultipartFile file, Course course, Model model) throws MessagingException, IOException {



        Teacher teacher = getCurrentUser().getTeacher();

        courseService.updateCourse(course, id);
        Course newCourse = courseService.getCourseById(id);


        List<Student> students = teacherService.getTeacherClassesByCourse(teacher,newCourse);
        for(Student student : students){
            emailSenderService.sendEmailToStudent(newCourse,student,teacher, file);
        }

        String message = "Your assignment has been successfully submited for " + newCourse.getCourseTitle();

        List<Course> courses = teacherService.getTeacherCourses(teacher);

        model.addAttribute("course", newCourse);
        model.addAttribute("courses", courses);
        model.addAttribute("message", message);

        return "teachers/assignments";
    }

    @GetMapping("/grades")
    public String grades(Model model, String message) {

        Result result = new Result();
        Teacher teacher = getCurrentUser().getTeacher();
        List<Result> teacherResults = teacherService.getTeacherResults(teacher);
        List<Student> students = teacherService.getTeacherClasses_FoGrades(teacherResults);
        
        model.addAttribute("message", message);
        model.addAttribute("result", result);
        model.addAttribute("students", students);
        return "teachers/grades";
    }

    @GetMapping("/grades/{id}")
    public String saveGradeIndividualy(@PathVariable Long id, Model model) {

        Teacher teacher = getCurrentUser().getTeacher();
        List<Result> teacherResults = teacherService.getTeacherResults(teacher);
        List<Course> courses = teacherService.getTeacherCourses(teacherResults);
        Result newResult = new Result();
        Student student = studentService.getStudentById(id);

        model.addAttribute("student", student);
        model.addAttribute("result", newResult);
        model.addAttribute("courses", courses);

        return "teachers/insert_grades";
    }

    @PostMapping("/grades/{id}")
    public String saveStudentGrades(@PathVariable Long id, Model model, Result result) {
        Student student = studentService.getStudentById(id);
        Result newResult = new Result();

        String message = teacherService.saveStudentGrades(result, student);

        Teacher teacher = getCurrentUser().getTeacher();
        List<Result> teacherResults = teacherService.getTeacherResults(teacher);

        model.addAttribute("message", message);
        model.addAttribute("results", teacherResults);
        model.addAttribute("result", newResult);
        return "redirect:/teacher/grades";
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
