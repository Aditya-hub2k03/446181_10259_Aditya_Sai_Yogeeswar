package com.xmldi.cisimix;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-cisimix.xml");
        Student student = context.getBean("student", Student.class);
        student.show();
    }
}
