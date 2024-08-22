package com.pryalkin.proxy;

import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.ServiceUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyUserService implements IProxy<ServiceUser, HttpResponse>{

    private ServiceUser serviceUser;

    public ProxyUserService(ServiceUser serviceUser) {
        this.serviceUser = serviceUser;
    }

    @Override
    public HttpResponse getResultMethod(String token, String methodname, Object... objs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Log log = new Log();
        Method method = Arrays.stream(serviceUser.getClass().getMethods()).filter(m -> m.getName().equals(methodname)).findFirst().get();
        log.setStart();
        HttpResponse httpResponse = (HttpResponse) method.invoke(serviceUser, objs);
        log.setEnd();
        log.getCompleted(methodname);
        return httpResponse;
    }
}
