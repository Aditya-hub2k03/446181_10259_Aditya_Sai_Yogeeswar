package com.xmldi.fi;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans-field.xml");

        Student student = (Student) context.getBean("student");
        student.show();
    }
}
