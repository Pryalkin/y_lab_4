package com.pryalkin.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface IProxy<T, V> {

    V getResultMethod(String token, String methodname, Object... objs) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException;
}
