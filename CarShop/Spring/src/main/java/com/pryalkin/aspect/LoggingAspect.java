package com.pryalkin.aspect;

import com.pryalkin.log.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Log log = new Log();

    @Pointcut("within(com.pryalkin.service.impl.*)")
    public void pointCut() {}

    @Before("pointCut()")
    public void before() {
        log.setStart();
    }

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        log.setEnd();
        log.getCompleted(joinPoint.getSignature().getName());
    }
}
