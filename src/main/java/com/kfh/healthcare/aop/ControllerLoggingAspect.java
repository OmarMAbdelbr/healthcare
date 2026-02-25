package com.kfh.healthcare.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllerLoggingAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Around("execution(* com.kfh.healthcare.controller.*.*(..)) && !within(com.kfh.healthcare.controller.AuthenticationController)")
    public Object logApiCall(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        log.info("API called : {} with arguments: {}", methodName, joinPoint.getArgs());

        Object result = joinPoint.proceed();

        long executionTime = System.currentTimeMillis() - start;
        log.info("API Returned : {} completed in {} ms", methodName, executionTime);

        return result;
    }
}
