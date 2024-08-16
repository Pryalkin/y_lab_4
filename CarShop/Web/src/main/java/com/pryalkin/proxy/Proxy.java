package com.pryalkin.proxy;


import com.pryalkin.service.ServiceAuth;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

public class Proxy{

    private Proxy() {
    }

    public static Object getResultMethod(Object o, Log log, String methodName, Object... objects) throws InvocationTargetException, IllegalAccessException {
        Object result = null;
        Method[] methods = o.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if(methods[i].getName().equals(methodName)){
                log.setStart();
                result = methods[i].invoke(o, objects);
                log.setEnd();
                log.getCompleted(methodName);
            }
        }
        return result;
    }
}
