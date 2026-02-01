package com.drawpicaa.oims.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Aspect
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* com.drawpicaa.oims.service.impl.OrderServiceImpl.*(..)) || execution(* com.drawpicaa.oims.service.impl.ProductServiceImpl.*(..)) ")
    public void logMethodBefore(JoinPoint jp){
        LOGGER.info("Logging(Before) : '"+jp.getSignature().getName() + "' method called. || "+ LocalTime.now().toString().substring(0,8));
    }

    @After("execution(* com.drawpicaa.oims.service.impl.OrderServiceImpl.*(..)) || execution(* com.drawpicaa.oims.service.impl.ProductServiceImpl.*(..)) ")
    public void logMethodAfter(JoinPoint jp){
        LOGGER.info("Logging(After) : '"+jp.getSignature().getName() + "' method called. || "+ LocalTime.now().toString().substring(0,8));
    }

    @AfterThrowing("execution(* com.drawpicaa.oims.service.impl.OrderServiceImpl.*(..)) || execution(* com.drawpicaa.oims.service.impl.ProductServiceImpl.*(..)) ")
    public void logMethodAfterThrowing(JoinPoint jp){
        LOGGER.info("Logging(After Throwing) : '"+jp.getSignature().getName() + "' method called. || "+ LocalTime.now().toString().substring(0,8));
    }

    @AfterReturning("execution(* com.drawpicaa.oims.service.impl.OrderServiceImpl.*(..)) || execution(* com.drawpicaa.oims.service.impl.ProductServiceImpl.*(..)) ")
    public void logMethodAfterReturning(JoinPoint jp){
        LOGGER.info("Logging(After Returning) : '"+jp.getSignature().getName() + "' method called. || "+ LocalTime.now().toString().substring(0,8));
    }


}
