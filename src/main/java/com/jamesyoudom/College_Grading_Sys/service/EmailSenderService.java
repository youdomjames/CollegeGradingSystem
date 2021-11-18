/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.model.Course;
import com.jamesyoudom.College_Grading_Sys.model.Student;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.jamesyoudom.College_Grading_Sys.model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author youdo
 */
@Service
public class EmailSenderService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmailToTeacher(String toEmail,
                                   Course courseFound,
                                   Student student,
                                   MultipartFile file) throws MessagingException, UnsupportedEncodingException{
        
        String subject = "Assignment file submitted by "+student.getFullName();
        String body = "<p>Hello M. "+courseFound.getTeacher().getFullName()+"</p>"
                        +"<p>Here is the file submitted by "+"<b>"+student.getFullName()+"</b>, "
                        +"for the assignment of the course: "+courseFound.getCourseTitle()+".</p>"
                        +"<p>The assigngment due date is: "+"<b>"+courseFound.getAssignment_DueDate()+"</b></p>"
                        +"<p>If there is any problem, please contact us.</p>"
                        + "<p>Best regards.</p> <hr>"
                        +"<p><b><i>Administration</i></b></p>"
                        +" <img src='cid:logoImage' height=\"50px\" width=\"50px\"/>";
        
        MimeMessage mimeMessage =  mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage, true);
        
        mimeMessageHelper.setFrom("codingprojects27@gmail.com", "Administration");
        mimeMessageHelper.setTo(toEmail);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);
        
        if(!file.isEmpty()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            
            InputStreamSource source = new InputStreamSource(){
                @Override
                public InputStream getInputStream() throws IOException {
                return file.getInputStream();
                }
            
        };
            
            mimeMessageHelper.addAttachment(fileName, source);
        }
        
         ClassPathResource resource = new ClassPathResource("/static/img/logo.png");
         mimeMessageHelper.addInline("logoImage", resource);

        mailSender.send(mimeMessage);
       
        
    }

    public void sendEmailToStudent(Course course,
                                   Student student,
                                   Teacher teacher,
                                   MultipartFile file) throws MessagingException, UnsupportedEncodingException{

        String subject = "New Assignment for "+course.getCourseTitle();
        String body = "<p>Hello "+student.getFullName()+"</p>"
                +"<p>Here is the assignment submitted by "+"<b>"+teacher.getFullName()+"</b>, "
                +"for : "+course.getCourseTitle()+".</p>"
                +"<p>The assigngment due date is: "+"<b>"+course.getAssignment_DueDate()+"</b></p>"
                +"<p>If there is any problem, please contact us.</p>"
                + "<p>Best regards.</p> <hr>"
                +"<p><b><i>Administration</i></b></p>"
                +" <img src='cid:logoImage' height=\"50px\" width=\"50px\"/>";

        MimeMessage mimeMessage =  mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper =new MimeMessageHelper(mimeMessage, true);

        mimeMessageHelper.setFrom("codingprojects27@gmail.com", "Administration");
        mimeMessageHelper.setTo(student.getEmail());
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);


        if(!file.isEmpty()){
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            InputStreamSource source = new InputStreamSource(){
                @Override
                public InputStream getInputStream() throws IOException {
                    return file.getInputStream();
                }

            };

            mimeMessageHelper.addAttachment(fileName, source);
        }


        ClassPathResource resource = new ClassPathResource("/static/img/logo.png");
        mimeMessageHelper.addInline("logoImage", resource);

        mailSender.send(mimeMessage);


    }
}
