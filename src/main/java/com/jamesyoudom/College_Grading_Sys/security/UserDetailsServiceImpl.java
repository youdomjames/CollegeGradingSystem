/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor........
 */

package com.jamesyoudom.College_Grading_Sys.security;

/**
 *
 * @author youdo
 */



import com.jamesyoudom.College_Grading_Sys.service.UsersService;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService  {
    
     @Autowired
    private UsersService usersService;
	
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Users users = usersService.findByUsersname(userName);
        if(users!=null) {
        	
        	String role = users.getRole();
                    	
            return User.builder()
            	.username(users.getUsername())
            	//change here to store encoded password in db...
            	.password( bCryptPasswordEncoder.encode(users.getPassword()) )
            	.disabled(users.isDisabled())
            	.accountExpired(users.isAccountExpired())
            	.accountLocked(users.isAccountLocked())
            	.credentialsExpired(users.isCredentialsExpired())
            	.roles(role)
            	.build();
        } else if(userName.equals("admin")) {
            return User.builder()
                    .username("admin")
                    .password(bCryptPasswordEncoder.encode("password"))
                    .disabled(false)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .roles("ADMIN")
                    .build();
        }else{
        
        	throw new UsernameNotFoundException("User Name is not Found");
        }   
    }
}

