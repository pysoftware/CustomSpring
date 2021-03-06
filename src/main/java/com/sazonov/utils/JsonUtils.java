package com.sazonov.utils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static com.sazonov.utils.ObjectUtils.*;

@Slf4j
public class JsonUtils {

    private static final String DEFAULT_JSON_FIELD_PATTERN = "\"%s\":\"%s\"";

    public static String objectToJson(Object obj)
            throws IllegalArgumentException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException("Object must be non null");
        }

        return parseObject(obj, obj.getClass().getDeclaredFields());
    }

    private static String getJsonValue(String fieldName, Object value, String pattern) {
        return String.format(pattern, fieldName, value);
    }

    public static String parseObject(Object obj, Field[] objFields)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < objFields.length; i++) {
            stringBuilder.append(parseField(obj, objFields[i]));

            if (objFields.length - i > 1) {
                stringBuilder.append(",");
            }
        }

        return stringBuilder.append("}").toString();
    }

    public static String parseField(Object obj, Field field)
            throws NoSuchMethodException,
            IllegalAccessException,
            InvocationTargetException {
        if (compareTypes(Collection.class, field.getType())) {
            String getterMethodName = "get" + StringUtils.capitalize(field.getName());
            StringBuilder arrayStringBuilder = new StringBuilder(String.format("\"%s\":[", field.getName()));
            Collection<?> collection = ((Collection<?>) Objects.requireNonNull(getMethodReturnValue(obj, getterMethodName)));
            int i = 0;
            for (Object collectionItem : collection) {
                i++;
                arrayStringBuilder.append(parseObject(collectionItem, collectionItem.getClass().getDeclaredFields()));
                if (collection.size() - i > 0) {
                    arrayStringBuilder.append(",");
                }
            }
            arrayStringBuilder.append("]");
            return arrayStringBuilder.toString();
            /**
             * ?????????? ?????????? ???????????? String, Integer, ..., etc
             */
        } else if (isFromPackage(field, "java.")) {
            String getterMethodName = "get" + StringUtils.capitalize(field.getName());
            return getJsonValue(
                    field.getName(),
                    getMethodReturnValue(obj, getterMethodName).toString(),
                    DEFAULT_JSON_FIELD_PATTERN
            );
            /**
             * ?????????? ?????????? ???????????????? ????????????
             */
        } else {
            return parseObject(obj, obj.getClass().getDeclaredFields());
        }
    }
}