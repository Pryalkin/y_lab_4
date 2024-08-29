package com.pryalkin.aspect;

import com.pryalkin.service.ServiceLoggingUser;
import com.pryalkin.service.impl.ServiceLoggingUserImpl;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class AuditAspect {

    private final ServiceLoggingUser serviceLoggingUser;

    public AuditAspect(ServiceLoggingUser serviceLoggingUser) {
        this.serviceLoggingUser = serviceLoggingUser;
    }

    @Pointcut("within(com.pryalkin.service.impl.ServiceOrderImpl)")
    public void pointCut() {}

    @After("pointCut()")
    public void after(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        // Ищем объект в аргументах
        for (Object arg : args) {
            if (arg instanceof String) { // Проверяем тип объекта
                String token = (String) arg;
                // Получаем значение из объекта
                System.out.println("Значение объекта MyObject: " + token);
            }
        }

//        serviceLoggingUser.saveLoggingUser(joinPoint.getSignature().getName(), joinPoint.getArgs());

    }


}
