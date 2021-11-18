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
import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.util.List;

/**
 *
 * @author youdo
 */
public interface TeacherService {
    
    List <Teacher> getAllTeachers ();
    
    Teacher saveTeacher(Teacher teacher);
	
    Teacher getTeacherById(Long id);
	
    Teacher updateTeacher(Teacher teacher, Long id);
    
    List <Teacher> getTeacherByKeyword(String keyword);
	
    void deleteTeacherById(Long id);
    
    Teacher simpleSave(Teacher teacher);
    
    /*********CUSTOM METHODS**********/
    
    String updateTeacherPassword(Users user, Users currentUser);
    
    
    List<Student> getTeacherClasses_ListOfStudents(List<Result> teacherResults);
    
    List<Course> getTeacherAssignments(Teacher teacher);
    
    List<Result> getTeacherResults(Teacher teacher);

    List<Student> getTeacherClassesByCourse(Teacher teacher, Course course);
    
    List<Student> getTeacherClasses_FoGrades(List<Result> teacherResults);
    
    List<Course> getTeacherCourses(List<Result> teacherResults);
    
    String saveStudentGrades(Result result, Student student);   
    
    
}
