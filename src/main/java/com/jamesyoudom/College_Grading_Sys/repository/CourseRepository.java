/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author youdo
 */
public interface CourseRepository extends JpaRepository <Course, Long> {
    
    @Query("SELECT c FROM Course c WHERE c.courseTitle LIKE %?1% or c.course_id LIKE %?1%")
    List<Course> findByKeyword(String keyword);
    
}
