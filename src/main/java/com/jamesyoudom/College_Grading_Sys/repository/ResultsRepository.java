/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.repository;

import com.jamesyoudom.College_Grading_Sys.model.Result;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author youdo
 */
public interface ResultsRepository extends JpaRepository<Result, Long> {
    
     @Query("SELECT r FROM Result r WHERE r.grade LIKE %?1% or r.course LIKE %?1%  or r.student LIKE %?1% ")
    List<Result> findByKeyword(String keyword);
    
    @Query("SELECT r FROM Result r WHERE r.student.student_id LIKE ?1")
    List<Result> findByStudentId(Long studentId);
}
