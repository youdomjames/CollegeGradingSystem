/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Student;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author youdo
 */
public interface StudentRepository extends JpaRepository <Student,Long>{
    
    	
@Query("SELECT s FROM Student s WHERE s.firstName LIKE %?1% or s.lastName LIKE %?1%")
    List<Student> findByKeyword(String keyword);
    
}
