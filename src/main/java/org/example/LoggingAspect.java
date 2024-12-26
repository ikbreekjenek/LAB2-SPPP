import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class LoggingAspect {

    // Хранилище для времени выполнения методов
    private final Map<String, Long> methodExecutionTimes = new ConcurrentHashMap<>();

    // Перехват методов, помеченных @Deprecated
    @Before("@annotation(java.lang.Deprecated)")
    public void logDeprecatedMethodCall() {
        System.out.println("Вызван устаревший метод.");
        Arrays.stream(Thread.currentThread().getStackTrace())
                .forEach(stackElement -> System.out.println(stackElement));
    }

    // Перехват методов из определенного пакета и замер времени выполнения
    @Around("execution(* com.example..*(..))") // Замените com.example на ваш пакет
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime();
        try {
            return joinPoint.proceed();
        } finally {
            long executionTime = System.nanoTime() - startTime;
            String methodName = joinPoint.getSignature().toShortString();
            methodExecutionTimes.merge(methodName, executionTime, Long::sum);
        }
    }

    // Выводим результаты в конце выполнения программы
    @After("execution(* com.example..*(..))") // Замените com.example на ваш пакет
    public void printExecutionTimes() {
        System.out.println("Время выполнения методов:");
        methodExecutionTimes.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(entry -> System.out.printf("%s: %d ns%n", entry.getKey(), entry.getValue()));
    }
}
