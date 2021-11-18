/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamesyoudom.College_Grading_Sys.configuration;

import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author youdo
 */
@Configuration
public class PaypalConfig {
   
    @Value("${paypal.client.id}")
    private String clientId;
    @Value("${paypal.client.secret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;
    
   @Bean
   public Map<String, String> paypalSdkConfig(){
       Map<String, String> configMap = new HashMap<>();
       configMap.put("mode", mode);
       return configMap;
   }
   
   @Bean
   public OAuthTokenCredential  oAuthTokenCredential(){
       return new OAuthTokenCredential(clientId,clientSecret,paypalSdkConfig());
   }
   @Bean
   public APIContext apiContext() throws PayPalRESTException{
       APIContext context = new APIContext(oAuthTokenCredential().getAccessToken());
       context.setConfigurationMap(paypalSdkConfig());
       return context;
   }
    
}
