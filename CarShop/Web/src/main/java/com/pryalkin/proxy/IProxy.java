package com.pryalkin.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public interface IProxy<T> {

    T getResultMethod(T t, Log log, String methodName, Parameter[] parameters) throws InvocationTargetException, IllegalAccessException;

}
