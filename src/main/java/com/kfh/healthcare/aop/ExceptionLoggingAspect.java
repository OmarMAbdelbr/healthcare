package com.kfh.healthcare.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ExceptionLoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @AfterThrowing(
            pointcut = "execution(* com.kfh.healthcare..*(..))",
            throwing = "exception"
    )
    public void logExceptions(JoinPoint joinPoint, Throwable exception) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        log.error("Exception caught by AOP Logger!");
        log.error("Method       : {}.{}()", className, methodName);
        log.error("Arguments    : {}", Arrays.toString(methodArgs));
        log.error("Exception    : {}", exception.getMessage(), exception);
    }
}
