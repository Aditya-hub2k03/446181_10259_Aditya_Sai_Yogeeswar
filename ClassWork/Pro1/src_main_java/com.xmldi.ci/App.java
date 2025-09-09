package com.xmldi.ci;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-constructor.xml");

        Student student = (Student) context.getBean("student");
        student.show();
    }
}
