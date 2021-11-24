/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *
 * @author youdo
 */
@Service
public interface ResultService {
    
    List <Result> getAllResults ();
    
    Result saveResult(Result result);
	
    Result getResultById(Long id);
	
    Result updateResult(Result result, Long id);
    
    List <Result> getResultByKeyword(String keyword);
	
    void deleteResultById(Long id);
    
    List<Result> findByStudentId(Long student_id);
}
