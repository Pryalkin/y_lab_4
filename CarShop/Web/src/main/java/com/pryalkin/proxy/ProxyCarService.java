package com.pryalkin.proxy;

import com.pryalkin.dto.response.HttpResponse;
import com.pryalkin.factory.Factory;
import com.pryalkin.model.User;
import com.pryalkin.service.ServiceAuth;
import com.pryalkin.service.ServiceCar;
import com.pryalkin.service.ServiceLoggingUser;
import com.pryalkin.service.ServiceUser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ProxyCarService implements IProxy<ServiceCar, HttpResponse> {

    private ServiceCar serviceCar;

    public ProxyCarService(ServiceCar serviceCar) {
        this.serviceCar = serviceCar;
    }

    @Override
    public HttpResponse getResultMethod(String token, String methodname, Object... objs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Log log = new Log();
        Method method = Arrays.stream(serviceCar.getClass().getMethods()).filter(m -> m.getName().equals(methodname)).findFirst().get();
        log.setStart();
        HttpResponse httpResponse = (HttpResponse) method.invoke(serviceCar, objs);
        log.setEnd();
        log.getCompleted(methodname);
        ServiceAuth serviceAuth = (ServiceAuth) Factory.getService("ServiceAuth");
        User user = serviceAuth.getUserByToken(token);
        ServiceLoggingUser serviceLoggingUser = (ServiceLoggingUser) Factory.getService("ServiceLoggingUser");
        serviceLoggingUser.saveLoggingUser(methodname, user.getId());
        return httpResponse;
    }
}
