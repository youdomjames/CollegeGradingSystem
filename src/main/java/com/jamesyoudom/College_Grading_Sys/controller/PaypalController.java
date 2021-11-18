/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.controller;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import com.jamesyoudom.College_Grading_Sys.model.Users_Payment;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.service.PaypalService;
import com.jamesyoudom.College_Grading_Sys.service.StudentService;
import com.jamesyoudom.College_Grading_Sys.service.TeacherService;
import com.jamesyoudom.College_Grading_Sys.service.UsersService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author youdo
 */
@Controller
public class PaypalController {

    @Autowired
    PaypalService paypalService;

    @Autowired
    TeacherService teacherService;
    
    @Autowired
    UsersService usersService;
    
    @Autowired
    StudentService studentService;

    public static final String SUCCESS_URL = "student/pay/success";
    public static final String CANCEL_URL = "student/pay/cancel";

    @GetMapping("/student/payment")
    public String paymentHome(Model model, String state) {
        Student student = getCurrentUser().getStudent();
        List<Course> courses = studentService.getStudentCourses(student.getResults());
        model.addAttribute("courses", courses);
        model.addAttribute("student", student); 
        model.addAttribute("state", state);   
        model.addAttribute("size", courses.size());
        return "students/tuition";
    }
    
    @PostMapping("/student/pay")
    public String payment(@RequestParam("amount") double amount, @RequestParam("intent") String intent, @RequestParam("currency") String currency, @RequestParam("method") String method, @RequestParam("description") String description ,Model model) throws PayPalRESTException {
        Payment payment = paypalService.createPayment(amount, currency, method, intent, description, "http://localhost:8286/" + CANCEL_URL, "http://localhost:8286/" + SUCCESS_URL);
        for (Links link : payment.getLinks()) {
            if (link.getRel().equals("approval_url")) {
                return "redirect:" + link.getHref();
            }
        }
        return "redirect:/student/payment";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "redirect:/student/payment";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, Model model) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {
                    Student student = getCurrentUser().getStudent();
                    Date date = new Date();  
                    student.setTuitionFees(0);
                    student.setLastPaymentDate(date);
                    studentService.simpleSave(student);
                
                String state = "success";
                model.addAttribute("state", state);
                return "redirect:/student/payment";
            } else{
                String state = "failed";
                model.addAttribute("state", state);
                return "redirect:/student/payment";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/student/payment";
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
