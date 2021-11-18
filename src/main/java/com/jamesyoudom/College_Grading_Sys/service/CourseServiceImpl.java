/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.....
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.repository.CourseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author youdo
 */
@Service
public class CourseServiceImpl implements CourseService { 
    @Autowired
    CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.getById(id);
    }

    @Override
    public Course updateCourse(Course course, Long id) {
        // get course from database by id
        Course existingCourse = getCourseById(id);
        existingCourse.setCourse_id(id);
        if (course.getCourseTitle() != null) {
            existingCourse.setCourseTitle(course.getCourseTitle());
        }

        if (course.getTeacher() != null) {
            existingCourse.setTeacher(course.getTeacher());
        }

        if (course.getAssignment_DueDate() != null) {
            existingCourse.setAssignment_DueDate(course.getAssignment_DueDate());
        }

        if (course.getAssignment_Note() != null) {
            existingCourse.setAssignment_Note(course.getAssignment_Note());
        }

        if (course.getResults() != null) {
            existingCourse.setResults(course.getResults());
        }  
        
        if (course.getPrice()!= 0) {
            existingCourse.setPrice(course.getPrice());
        }

        return courseRepository.save(existingCourse);
    }

    @Override
    public List<Course> getCourseByKeyword(String keyword) {
        return courseRepository.findByKeyword(keyword);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);

    }
}
