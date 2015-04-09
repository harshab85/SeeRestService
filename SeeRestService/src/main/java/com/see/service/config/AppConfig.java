package com.see.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@ComponentScan("com.see.service.controller")
@Configuration 
@EnableWebMvc   
public class AppConfig {  
} 