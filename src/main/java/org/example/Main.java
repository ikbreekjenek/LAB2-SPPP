package org.example;

import org.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        TestService testService = context.getBean(TestService.class);
        testService.deprecatedMethod();
        testService.normalMethod();
        testService.longRunningMethod();

        context.close();
    }
}
