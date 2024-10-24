package com.example.kahoot.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {
    @Before("execution(* com.example.kahoot.controller..*.*(..))")
    public void logBeforeController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("→ Appel de {} dans {}", methodName, className);
    }

    @AfterReturning(
            pointcut = "execution(* com.example.kahoot.controller..*.*(..))",
            returning = "result"
    )
    public void logAfterController(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("← Fin de {} avec succès", methodName);
    }

    @AfterThrowing(
            pointcut = "execution(* com.example.kahoot.controller..*.*(..))",
            throwing = "exception"
    )
    public void logError(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("✕ Erreur dans {} : {}", methodName, exception.getMessage());
    }
}