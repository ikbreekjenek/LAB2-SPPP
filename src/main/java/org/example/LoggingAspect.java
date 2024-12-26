package org.example;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Логирование вызова @Deprecated методов
    @Before("@annotation(java.lang.Deprecated)")
    public void logDeprecatedMethod() {
        System.out.println("Deprecated method executed");
    }

    // Логирование времени выполнения
    @Around("execution(* org.example.*.*(..))") // Пакет, в котором находятся методы
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // выполняем метод
        long timeTaken = System.currentTimeMillis() - start;

        // Выводим информацию о времени выполнения
        System.out.println("Execution time for " + joinPoint.getSignature() + " is " + timeTaken + "ms");

        return result;
    }
}
