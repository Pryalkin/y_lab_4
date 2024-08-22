package com.pryalkin.proxy;


import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.impl.ServiceAuthImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyAuthService implements IProxy<ServiceAuth, HttpResponse> {

    private ServiceAuth serviceAuth;

    public ProxyAuthService(ServiceAuth serviceAuth) {
        this.serviceAuth = serviceAuth;
    }

    @Override
    public HttpResponse getResultMethod(String token, String methodname, Object... objs) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Log log = new Log();
        Method method = Arrays.stream(serviceAuth.getClass().getMethods()).filter(m -> m.getName().equals(methodname)).findFirst().get();
        log.setStart();
        HttpResponse httpResponse = (HttpResponse) method.invoke(serviceAuth, objs);
        log.setEnd();
        log.getCompleted(methodname);
        return httpResponse;
    }
}
