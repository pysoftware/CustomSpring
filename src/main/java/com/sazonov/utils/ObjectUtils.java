package com.sazonov.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Slf4j
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

    public static boolean objectHasAnnotation(Class<?> object, Class<?> needfulAnn) {
        Objects.requireNonNull(object);

        Annotation[] annotations = object.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            Class<?> annType = annotation.annotationType();
            // Костыль
            if (annType.equals(Retention.class) || annType.equals(Documented.class) || annType.equals(Target.class)) {
                continue;
            }
            if (annotation.annotationType().equals(needfulAnn)) {
                return true;
            }
            return objectHasAnnotation(annotation.annotationType(), needfulAnn);
        }
        return false;
    }

    public static boolean isFromPackage(Object object, String packageName) {
        return object.getClass().getPackage().getName().contains(packageName);
    }
}
