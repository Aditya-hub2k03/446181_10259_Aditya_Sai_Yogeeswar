package com.yp.pro1;

import org.springframework.stereotype.Component;

@Component("greetingService")  // Annotation-based bean
public class GreetingServiceImpl implements GreetingService {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name + "! This is annotation-based Spring bean.");
    }
}
