package com.pryalkin.proxy;

import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.ServiceLoggingUser;
import com.pryalkin.service.ServiceOrder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyOrderService implements IProxy<ServiceOrder, HttpResponse>{

    private ServiceOrder serviceOrder;

    public ProxyOrderService(ServiceOrder serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    @Override
    public HttpResponse getResultMethod(String token, String methodname, Object... objs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Log log = new Log();
        Method method = Arrays.stream(serviceOrder.getClass().getMethods()).filter(m -> m.getName().equals(methodname)).findFirst().get();
        log.setStart();
        HttpResponse httpResponse = (HttpResponse) method.invoke(serviceOrder, objs);
        log.setEnd();
        log.getCompleted(methodname);
        ServiceAuth serviceAuth = (ServiceAuth) Factory.getService("ServiceAuth");
        User user = serviceAuth.getUserByToken(token);
        ServiceLoggingUser serviceLoggingUser = (ServiceLoggingUser) Factory.getService("ServiceLoggingUser");
        serviceLoggingUser.saveLoggingUser(methodname, user.getId());
        return httpResponse;
    }

}
