package com.caiguantianxia.service.aop;

import com.caiguantianxia.service.threadlocal.ReqInfo;
import com.caiguantianxia.service.threadlocal.ReqInfoOperator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

/**
 * @author : Hongtu Zang
 * @date : Created in 下午6:12 19-4-24
 */
@Aspect
@Component
public class ControllerAOP {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public * com.caiguantianxia..*.controller..*.*(..))")
    public void controllerMethod() {

    }

    @Around("controllerMethod()")
    public Object handleControllerMethod(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ReqInfo reqInfo = ReqInfoOperator.get();
        if (reqInfo == null) {
            reqInfo = new ReqInfo();
        }
        if (StringUtils.isEmpty(reqInfo.getRequestId())) {
            reqInfo.setRequestId(UUID.randomUUID().toString());
        }
        ReqInfoOperator.set(reqInfo);
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
