/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Result;
import com.jamesyoudom.College_Grading_Sys.model.Student;
import com.jamesyoudom.College_Grading_Sys.repository.ResultsRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author youdo
 */
@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseService courseService;

    @Autowired
    ResultsRepository resultsRepository;

    @Override
    public List<Result> getAllResults() {

        return resultsRepository.findAll();
    }

    @Override
    public Result saveResult(Result result) {

        Student studentFromDb = studentService.getStudentById(result.getStudent().getStudent_id());
        Course courseFromDb = courseService.getCourseById(result.getCourse().getCourse_id());
        result.setStudent(studentFromDb);
        result.setCourse(courseFromDb);
        return resultsRepository.save(result);
    }

    @Override
    public Result getResultById(Long id) {

        return resultsRepository.getById(id);
    }

    @Override
    public Result updateResult(Result result, Long id) {
        return resultsRepository.save(result);
    }

    @Override
    public List<Result> getResultByKeyword(String keyword) {
        return resultsRepository.findByKeyword(keyword);
    }

    @Override
    public void deleteResultById(Long id) {
        resultsRepository.deleteById(id);
    }

    @Override
    public List<Result> findByStudentId(Long student_id) {
        return resultsRepository.findByStudentId(student_id);
    }

}
