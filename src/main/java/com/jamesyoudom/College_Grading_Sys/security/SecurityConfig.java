/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author youdo
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserDetailsService userDetailsService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/videos/*", "/home", "/css/*", "/js/*", "/img/logo.png").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/student/**").hasRole("STUDENT")
                .antMatchers("/teacher/**").hasRole("TEACHER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=true")
                .and()
                .exceptionHandling().accessDeniedPage("/login?accessDenied=true")
                .and()
                .rememberMe()
                .and()
                .logout()
                .logoutUrl("/logout")    
                .logoutSuccessUrl("/login?logout=true")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID", "remember-me")
                .logoutSuccessUrl("/home");
    }


        @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
    
@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}
   
}



