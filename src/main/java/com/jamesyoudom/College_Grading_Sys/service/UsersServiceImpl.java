/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.service;

import com.jamesyoudom.College_Grading_Sys.security.Users;
import com.jamesyoudom.College_Grading_Sys.security.UsersRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author youdo
 */
@Service
public class UsersServiceImpl implements UsersService{

    @Autowired
    UsersRepository userRepo;
    @Override
    public Users findByUsersname(String username) {
        return userRepo.findByusername(username);
    }

    @Override
    public List<Users> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public Users saveUsers(Users users) {
    return userRepo.save(users);
    }

    @Override
    public Users getUsersId(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public Users updateUsers(Users users) {
        return userRepo.save(users);
    }

    @Override
    public List<Users> getUsersByKeyword(String keyword) {
        return userRepo.findByKeyword(keyword);
    }

    @Override
    public void deleteUsersById(Long id) {
        userRepo.deleteById(id);
    }
    
}
