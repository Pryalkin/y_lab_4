package com.pryalkin.aspect;

import com.pryalkin.annotation.Token;
import com.pryalkin.client.AuditClient;
import com.pryalkin.client.SecurityClient;
import com.pryalkin.dto.UserAudit;
import com.pryalkin.dto.request.LoggingUserRequestDTO;
import com.pryalkin.dto.request.TokenRequestDTO;
import com.pryalkin.dto.response.UserResponseDTO;
import com.pryalkin.mapper.UserAuditMapper;
import com.pryalkin.service.AuthService;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;

@Component
@Aspect
@AllArgsConstructor
public class AuditAspect {

    private final AuditClient auditClient;
    private final SecurityClient securityClient;
    private final UserAuditMapper userAuditMapper;
    private final AuthService authService;

    @Pointcut("within(com.pryalkin.service.impl.OrderServiceImpl)")
    public void pointCut() {}

    @AfterReturning("pointCut()")
    public void after(JoinPoint joinPoint) {
        String token = null;
        // Получаем список аргументов
        Object[] args = joinPoint.getArgs();
        // Получаем сигнатуру метода
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // Получаем метод по имени
        try {
            Method method = joinPoint.getTarget().getClass().getMethod(signature.getName(), signature.getParameterTypes());
            for (int i = 0; i < method.getParameters().length; i++) {
                if(method.getParameters()[i].getName().equals("token")){
                    token = (String )joinPoint.getArgs()[i];
                }
            }
        } catch (NoSuchMethodException e) {
            // Обработка ошибки, если метод не найден
            e.printStackTrace(); // Вставьте обработку ошибок
        }
        UserResponseDTO userResponseDTO = securityClient.getUserWithToken(new TokenRequestDTO(token), authService.getToken());
        UserAudit userAudit = userAuditMapper.userResponseDtoToUserAudit(userResponseDTO);
        LoggingUserRequestDTO loggingUserRequestDTO = new LoggingUserRequestDTO();
        loggingUserRequestDTO.setUserAudit(userAudit);
        loggingUserRequestDTO.setAction(joinPoint.getSignature().getName());
        loggingUserRequestDTO.setDate(new Date());
        auditClient.send(loggingUserRequestDTO);
    }
}
