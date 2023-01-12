package com.stackroute.profile.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Aspect
@Component
public class LoggerAspect {
    /*
     * Write loggers for each of the methods of ProjectController, any particular method
     * will have all the four aspectJ annotation
     * (@Before, @After, @AfterReturning, @AfterThrowing).
     */
    private static final Logger logger = LoggerFactory.getLogger(LoggerAspect.class);

    @Pointcut("execution (* com.stackroute.profile.controller.UserProfileController.*(..))")
    public void allControllerMethods() {
        // pointcut method
    }

    @Before("allControllerMethods()")
    public void beforeAdvice(JoinPoint joinPoint) {
        logger.info("**********@Before**********");
        logger.debug("Method Name: {}", joinPoint.getSignature().getName());
        logger.debug("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
        logger.info("**************************");
    }

    @After("allControllerMethods()")
    public void afterAdvice(JoinPoint joinPoint) {
        logger.info("**********@After**********");
        logger.debug("Method Name: {}", joinPoint.getSignature().getName());
        logger.debug("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
        logger.info("**************************");
    }

    @AfterReturning(value="allControllerMethods()",returning = "result")
    public void afterAdvice(JoinPoint joinPoint, Object result) {
        logger.info("**********@AfterReturning**********");
        logger.debug("Method Name: {}", joinPoint.getSignature().getName());
        logger.debug("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
        logger.debug("Return Value: {}", result);
        logger.info("**************************");
    }

    @AfterThrowing(value="allControllerMethods()",throwing = "error")
    public void afterAdvice(JoinPoint joinPoint, Throwable error) {
        logger.info("**********@AfterThrowing**********");
        logger.debug("Method Name: {}", joinPoint.getSignature().getName());
        logger.debug("Method Args: {}", Arrays.toString(joinPoint.getArgs()));
        logger.debug("Exception: {}", error);
        logger.info("**************************");
    }
}
