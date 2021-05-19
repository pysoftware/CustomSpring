package com.sazonov.utils;

import java.lang.reflect.InvocationTargetException;

public final class ObjectUtils {

    public static boolean compareTypes(Class<?> clazz, Class<?> clazz1) {
        return clazz.isAssignableFrom(clazz1);
    }

    public static Object getMethodReturnValue(Object obj, String methodName)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        try {
            return obj.getClass().getMethod(methodName).invoke(obj);
        } catch (NoSuchMethodException noSuchMethodException) {
            throw new NoSuchMethodException(methodName + "() method doesn't exists in " + obj.getClass());
        }
    }

    public static boolean isFromPackage(Object object, String packageName) {
        return object.getClass().getPackage().getName().contains(packageName);
    }
}
