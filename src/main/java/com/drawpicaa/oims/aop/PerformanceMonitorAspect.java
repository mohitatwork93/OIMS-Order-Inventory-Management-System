package com.drawpicaa.oims.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class PerformanceMonitorAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerformanceMonitorAspect.class);

    @Around("execution(* com.drawpicaa.oims.service.impl.OrderServiceImpl.*(..))")
    public Object monitorTime(ProceedingJoinPoint pjp) {

        long start;
        long end;

        Object obj;
        try {
            start = System.currentTimeMillis();
            obj = pjp.proceed();
            end = System.currentTimeMillis();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }



        LOGGER.info("Time Taken: " + (end - start) + " ms");

        return obj;
    }
}
