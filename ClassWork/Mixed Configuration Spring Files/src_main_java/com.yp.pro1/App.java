package com.yp.pro1;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        // Annotation-based bean
        GreetingService greetingService = context.getBean("greetingService", GreetingService.class);
        greetingService.sayHello("Yogesh");

        // XML-based beans
        System.out.println("Conetxt Based Bean");
        User user1 = context.getBean("userid1", User.class);
        User user2 = context.getBean("userid2", User.class);

        System.out.println(user1);
        System.out.println(user2);
    }
}
