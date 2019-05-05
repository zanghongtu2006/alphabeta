package com.zanghongtu.service.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:12 19-4-24
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public * com.zanghongtu..*.controller..*.*(..))")
    public void controllerMethod() {

    }

    @Around("controllerMethod()")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            Object obj = proceedingJoinPoint.proceed();
            logger.info(proceedingJoinPoint.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
            return obj;
        } catch (Throwable throwable) {
            logger.error("Execute " + proceedingJoinPoint.getSignature() + " failed.", throwable);
            throw throwable;
        }
    }

}
