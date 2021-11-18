/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.security.Users;
import java.util.List;

/**
 *
 * @author youdo
 */
public interface UsersService {
    
    Users findByUsersname(String username);
    List <Users> getAllUsers ();
    
    Users saveUsers(Users users);
	
    Users getUsersId(Long id);
	
    Users updateUsers(Users users);
    
    List <Users> getUsersByKeyword(String keyword);
	
    void deleteUsersById(Long id);
    
}
