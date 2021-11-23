/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor..
 */
package com.jamesyoudom.College_Grading_Sys.model;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author youdo
 */
@Entity
public class Course implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long course_id;
    private String courseTitle;
    private String teacher_Name;
    private String assignment_Note;
    private String assignment_DueDate;
    private double price;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="teacher_id", referencedColumnName="teacher_id")
    private Teacher teacher;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    List<Result> results;
      
    

    public Course() {
    }

    public Course(String courseTitle, double price) {
        this.courseTitle = courseTitle;
        this.price = price;
    }

    public Course(Long course_id, String courseTitle, String teacher_Name, String assignment_Note, String assignment_DueDate, Teacher teacher, List<Result> results, double price) {
        this.course_id = course_id;
        this.courseTitle = courseTitle;
        this.teacher_Name = teacher_Name;

        this.assignment_Note = assignment_Note;
        this.assignment_DueDate = assignment_DueDate;
        this.teacher = teacher;
        this.results = results;
        this.price = price;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getTeacher_Name() {
        return teacher_Name;
    }

    public void setTeacher_Name(String teacher_Name) {
        this.teacher_Name = teacher_Name;
    }

    public String getAssignment_Note() {
        return assignment_Note;
    }

    public void setAssignment_Note(String assignment_Note) {
        this.assignment_Note = assignment_Note;
    }

    public String getAssignment_DueDate() {
        return assignment_DueDate;
    }

    public void setAssignment_DueDate(String assignment_DueDate) {
        this.assignment_DueDate = assignment_DueDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
