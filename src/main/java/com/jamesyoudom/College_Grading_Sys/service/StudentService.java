/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author youdo
 */

public interface StudentService {
    
    List <Student> getAllStudents ();
    
    Student saveStudent(Student student);
	
    Student getStudentById(Long id);
	
    Student updateStudent(Student student, Long id);
    
    List <Student> getStudentByKeyword(String keyword);
	
    void deleteStudentById(Long id);
    
    Student simpleSave(Student student);
    
    
    /*********CUSTOM METHODS********/
    String addStudentsCourse(Long courseId, Long id);
    
    String updateStudentPassword(Users user, Users currentUser);
    
    String getStudentCourses_message(List<Result> results);
    
    String getStudentResults(List<Result> results);
    
    List<Course> getStudentCourses(List<Result> results);
    
    List<Course> getStudentAssignments(List<Result> results);
    
    List<Student> finalGradeCalculation();
    
    String sendStudentAssignment(Student student, Long id, MultipartFile file)throws MessagingException, UnsupportedEncodingException;
}
