/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor..
 */
package com.jamesyoudom.College_Grading_Sys.model;

import java.util.List;
import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


/**
 *
 * @author youdo
 */
@Entity
public class Student {
   @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long student_id;    
    private String firstName, lastName,fullName=firstName+" "+lastName,badgeName=firstName, organisation, address_StreetName, city, province, postalCode, country, fullAddress, phoneNumber, fax, email, gender, dateOfBirth, final_Grade;
    private double tuitionFees;
    private Date lastPaymentDate;
    
    @OneToOne(cascade = CascadeType.ALL,
              fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
     private Users user;

       @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
    List<Result> results;
    

    public Student() {
    }

    public Student(Long student_id, String firstName, String lastName, String organisation, String address_StreetName, String city, String province, String postalCode, String country, String fullAddress, String phoneNumber, String fax, String email, String gender, String dateOfBirth, String final_Grade, Users user, List<Result> results, double tuitionFees, Date lastPaymentDate) {
        this.student_id = student_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organisation = organisation;
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
        this.final_Grade = final_Grade;
        this.user = user;
        this.results = results;
        this.tuitionFees = tuitionFees;
        this.lastPaymentDate = lastPaymentDate;
        
    }

   
    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getFinal_Grade() {
        return final_Grade;
    }

    public void setFinal_Grade(String final_Grade) {
        this.final_Grade = final_Grade;
    }

    public double getTuitionFees() {
        return tuitionFees;
    }

    public void setTuitionFees(double tuitionFees) {
        this.tuitionFees = tuitionFees;
    }

    public Date getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(Date lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    } 
    
}
