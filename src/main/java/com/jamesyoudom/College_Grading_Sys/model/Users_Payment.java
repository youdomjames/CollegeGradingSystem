/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.model;

import java.util.Date;

/**
 *
 * @author youdo
 */

public class Users_Payment {

    private double amount;
    private String currency ="CAD", method="paypal", intent="sale", description;
    
  
    public Users_Payment() {
    }

    public Users_Payment(double amount, String currency, String method, String intent, String description, Date date) {
        this.amount = amount;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }   

         
}
