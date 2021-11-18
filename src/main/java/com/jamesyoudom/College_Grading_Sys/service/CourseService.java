/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import java.util.List;

/**
 *
 * @author youdo
 */

public interface CourseService {
    
    List <Course> getAllCourses ();
    
    Course saveCourse(Course course);
	
    Course getCourseById(Long id);
	
    Course updateCourse(Course course, Long id);
    
    List <Course> getCourseByKeyword(String keyword);
	
    void deleteCourseById(Long id);
    
}
