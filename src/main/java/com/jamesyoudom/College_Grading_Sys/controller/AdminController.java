/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor...
 */
package com.jamesyoudom.College_Grading_Sys.controller;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.service.CourseService;
import com.jamesyoudom.College_Grading_Sys.service.ResultService;
import com.jamesyoudom.College_Grading_Sys.service.StudentService;
import com.jamesyoudom.College_Grading_Sys.service.TeacherService;
import com.jamesyoudom.College_Grading_Sys.service.UsersService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author youdo.
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

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

    /* Result Service*/
    @Autowired
    ResultService resultService;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @GetMapping("/home")
    public String getIndex(Principal principal, Model model) {
        String username = principal.getName();
        Users user = usersService.findByUsersname(username);
        model.addAttribute("user", user);
        return "admin_index";
    }

    /**
     * **************STUDENTS REQUEST HANDLER***************
     */
    // handler method to handle list students and return mode and view.......
    @GetMapping("/students")
    public String listStudents(String keyword, Model model) {
        model.addAttribute("student", new Student());
        if (keyword != null) {
            model.addAttribute("students", studentService.getStudentByKeyword(keyword));
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        return "students";
    }

    @GetMapping("/add/student")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "studentForm";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveStudent(student);
        return "redirect:/admin/students";
    }  

    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        model.addAttribute("student", studentService.getStudentById(id));
        return "edit_studentForm";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student, Model model) {
        studentService.updateStudent(student, id);
        return "redirect:";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return "redirect:";     
    }  
    
    @GetMapping("/results")
    public String getResults(Model model){
        
        List<Student> students = studentService.finalGradeCalculation();
        
        model.addAttribute("students", students);
        
        return"results";
    }

    /**
     * **************COURSES REQUEST HANDLER*******************
     */
    @GetMapping("/courses")
    public String listCourses(String keyword, Model model) {
        model.addAttribute("course", new Course());
        if (keyword != null) {
            model.addAttribute("courses", courseService.getCourseByKeyword(keyword));
        } else {
            model.addAttribute("courses", courseService.getAllCourses());
        }
        return "courses";
    }

    @GetMapping("/add/course")
    public String createCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "courseForm";
    }

    @PostMapping("/courses")
    public String saveCourse(@ModelAttribute("course") Course course, @ModelAttribute("teacher") Teacher teacher) {
        course.setTeacher(teacher);
        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        model.addAttribute("course", courseService.getCourseById(id));
        return "edit_courseForm";
    }

    @PostMapping("/courses/{id}")
    public String updateCourse(@PathVariable Long id,
            @ModelAttribute("course") Course course,
            Model model) {
        courseService.updateCourse(course, id);
        return "redirect:";
    }

    @GetMapping("/students/course")
    public String addStudentstoCourse(String keyword, Model model) {
        if (keyword != null) {
            model.addAttribute("students", studentService.getStudentByKeyword(keyword));
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        return "students_courses";
    }

    @GetMapping("/students/course/{id}")
    public String getStudentsCourses(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        model.addAttribute("courses", courseService.getAllCourses());
        return "course_addStudent";
    }   

    @PostMapping("/students/course/{id}")
    public String addStudentstoCourse(@PathVariable Long id, Long courseId, Model model) {
        model.addAttribute("message", studentService.addStudentsCourse(courseId, id));
        model.addAttribute("students",studentService.getAllStudents());
        return "students_courses";
    }

    @GetMapping("/courses/{id}")
    public String deleteCourse(@PathVariable Long id) {
        courseService.deleteCourseById(id);
        return "redirect:";
    }

    /**
     * **************TEACHERS REQUEST HANDLER**************
     */
    @GetMapping("/teachers")
    public String listTeachers(String keyword, Model model) {
        model.addAttribute("course", new Course());
        if (keyword != null) {
            model.addAttribute("teachers", teacherService.getTeacherByKeyword(keyword));
        } else {
            model.addAttribute("teachers", teacherService.getAllTeachers());
        }
        return "teachers";
    }

    @GetMapping("/add/teacher")
    public String createTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacherForm";
    }

    @PostMapping("/teachers")
    public String saveTeacher(@ModelAttribute("teacher") Teacher teacher) {
        teacherService.saveTeacher(teacher);
        return "redirect:/admin/teachers";
    }

    @GetMapping("/teachers/edit/{id}")
    public String editTeacherForm(@PathVariable Long id, Model model) {
        model.addAttribute("teacher", teacherService.getTeacherById(id));
        return "edit_teacherForm";
    }

    @PostMapping("/teachers/{id}")
    public String updateTeacher(@PathVariable Long id,
            @ModelAttribute("teacher") Teacher teacher,
            Model model) {
        teacherService.updateTeacher(teacher, id);
        return "redirect:";
    }

    @GetMapping("/teachers/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacherById(id);
        return "redirect:";
    }

    /**
     * **************USERS REQUEST HANDLER**************
     */
    @GetMapping("/users")
    public String listUsers(String keyword, Model model) {

        if (keyword != null) {
            List<Users> usersFound = usersService.getUsersByKeyword(keyword);
            model.addAttribute("users", usersFound);
        } else {
            List<Users> users = usersService.getAllUsers();
            model.addAttribute("users", users);
        }
        return "users";
    }

    @GetMapping("/add/user")
    public String createUserForm(Model model) {
        // create user 
        Users user = new Users();
        model.addAttribute("user", user);
        return "userForm";
    }

    @PostMapping("/users")
    public String saveUser(@ModelAttribute("user") Users user) {
        usersService.saveUsers(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", usersService.getUsersId(id));
        return "edit_userForm";
    }

    /*
    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable Long id,
            @ModelAttribute("user") Users user,
            Model model) {

        // get user from database by id.
        Users existingUser = usersService.getUsersId(id);
        existingUser.setUserId(user.getUserId());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRoles(user.getRoles());
        // save updated user object.
        usersService.saveUsers(existingUser);
        return "redirect:";
    }
     */

 /*Generate new password*/
    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        usersService.deleteUsersById(id);
        return "redirect:";
    }

    /**
     * **************ERRORS REQUEST HANDLER**************
     */
 /*   @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error_404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error_500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error_403";
            }
        }
        return "error";
    }*/
}
