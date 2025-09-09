package com.yogesh.javabased;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Address address() {
        Address address = new Address();
        address.setCity("New York");
        address.setState("NY");
        return address;
    }

    @Bean
    public Student student() {
        Student student = new Student();
        student.setId(101);
        student.setName("John Doe");
        student.setAddress(address());  
        return student;
    }
}

