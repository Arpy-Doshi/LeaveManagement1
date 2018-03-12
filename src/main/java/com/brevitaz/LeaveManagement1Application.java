package com.brevitaz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.brevitaz")
public class LeaveManagement1Application {

    public static void main(String[] args) {
        SpringApplication.run(LeaveManagement1Application.class, args);
    }
}




