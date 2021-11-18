/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.security;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author youdo
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

    Users findByusername(String username);
    
    @Query("SELECT u FROM Users u WHERE u.firstName LIKE %?1% or u.lastName LIKE %?1%")
    List<Users> findByKeyword(String keyword);
}
