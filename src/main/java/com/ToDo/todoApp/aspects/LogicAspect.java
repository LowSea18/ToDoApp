package com.ToDo.todoApp.aspects;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
@EnableAspectJAutoProxy
public class LogicAspect {
//to fix
    @Around("execution(* com.ToDo.todoApp.Services.Controllers.TaskControllers..*(..))")
    Object aroundTaskController(ProceedingJoinPoint jp) throws Throwable {
        long start = System.nanoTime();
        Object p = jp.proceed();
        log.info("Time :" + (System.nanoTime() - start));
        return p;

    }
}
