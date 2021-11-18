/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor..
 */
package com.jamesyoudom.College_Grading_Sys.model;

import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 *
 * @author youdo/
 */@Entity
public class Teacher implements Serializable {
    
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     private Long teacher_id;
     private String firstName, lastName,fullName, address_StreetName, city, province, postalCode, country, fullAddress, phoneNumber, fax, email, gender, dateOfBirth;
     private int salary;
     private Date last_PaymentDate;
     
    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
     private Users user;
     
     @OneToMany(cascade = CascadeType.ALL, mappedBy="teacher")
     private List<Course> courses;
     
     

    public Teacher() {
    }

    public Teacher(Long teacher_id, String firstName, String lastName, String fullName, String address_StreetName, String city, String province, String postalCode, String country, String fullAddress, String phoneNumber, String fax, String email, String gender, String dateOfBirth, int salary, Users user, List<Course> courses, Date last_PaymentDate) {
        this.teacher_id = teacher_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.address_StreetName = address_StreetName;
        this.city = city;
        this.province = province;
        this.postalCode = postalCode;
        this.country = country;
        this.fullAddress = fullAddress;
        this.phoneNumber = phoneNumber;
        this.fax = fax;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.salary = salary;
        this.user = user;
        this.courses = courses;
        this.last_PaymentDate = last_PaymentDate;
        
    }

   

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress_StreetName() {
        return address_StreetName;
    }

    public void setAddress_StreetName(String address_StreetName) {
        this.address_StreetName = address_StreetName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    
    
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getLast_PaymentDate() {
        return last_PaymentDate;
    }

    public void setLast_PaymentDate(Date last_PaymentDate) {
        this.last_PaymentDate = last_PaymentDate;
    }
     
    
     
}
