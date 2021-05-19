package com.sazonov.utils;


import com.google.common.base.Stopwatch;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.sazonov.utils.ObjectUtils.*;

@Slf4j
public class JsonUtils {

    private static final String DEFAULT_JSON_FIELD_PATTERN = "\"%s\":\"%s\"";
    private static final String ARRAY_JSON_FIELD_PATTERN = "\"%s\":\"%s\"";

    public static String objectToJson(Object obj)
            throws IllegalArgumentException,
            NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        if (Objects.isNull(obj)) {
            throw new IllegalArgumentException("Object must be non null");
        }
        StringBuilder stringBuilder = new StringBuilder("{");

        String getterMethodName;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
//            getterMethodName = "get" + StringUtils.capitalize(fields[i].getName());
//            // Field is a collection
//            if (compareTypes(Collection.class, fields[i].getType())) {
//                StringBuilder arrayStringBuilder = new StringBuilder(String.format("\"%s\":[", fields[i].getName()));
//                Collection<?> collection = ((Collection<?>) Objects.requireNonNull(getMethodReturnValue(obj, getterMethodName)));
//                int j = 0;
//                String s = collection.stream().map(Object::toString).collect(Collectors.joining(","));
////                for (Object item : collection) {
//////                    log.info(collection.size() + " " + j + " " + (collection.size() - j));
////                    j++;
////                    arrayStringBuilder.append(item.toString());
////                    if (collection.size() - j > 1) {
////                        log.info("fha");
////                        arrayStringBuilder.append("13,");
////                    }
////                }
//                arrayStringBuilder.append("]");
//                stringBuilder.append(arrayStringBuilder);
//            } else {
//                stringBuilder.append(
//                        getJsonValue(
//                                fields[i].getName(),
//                                getMethodReturnValue(obj, getterMethodName).toString(),
//                                DEFAULT_JSON_FIELD_PATTERN
//                        )
//                );
//            }
//            if (fields.length - i > 1) {
//                stringBuilder.append(",");
//            }
            stringBuilder.append(parseObject(obj, fields[i]));
            if (fields.length - i > 1) {
                stringBuilder.append(",");
            }
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }

    private static String getJsonValue(String fieldName, Object value, String pattern) {
        return String.format(pattern, fieldName, value);
    }

    public static String parseObject(Object obj, Field field) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (isFromPackage(field, "java.")) {
            String getterMethodName = "get" + StringUtils.capitalize(field.getName());
            log.info("java.lang {}", field);
//            return field.toString();
            return getJsonValue(
                    field.getName(),
                    getMethodReturnValue(obj, getterMethodName).toString(),
                    DEFAULT_JSON_FIELD_PATTERN
            );
        } else {
            StringBuilder stringBuilder = new StringBuilder("{");

            Field[] fields = field.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                stringBuilder.append(
                        String.format(
                                DEFAULT_JSON_FIELD_PATTERN,
                                fields[i].getName(),
                                parseObject(fields[i].getDeclaringClass(), fields[i])
                        )
                );

                if (fields.length - i > 1) {
                    stringBuilder.append(",");
                }
            }

            return stringBuilder.append("}").toString();
        }
    }


}

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class Person {
    private String name;
    private int age;
    private List<Book> books;
}

@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
class Book {
    private String name;

    public Book setBookNameee(String name) {
        this.name = name;
        return this;
    }
}

@Slf4j
class test {
    public static void main(String[] args) throws Exception {
//        log.info(String.valueOf(Integer.class.getPackage().getName().matches("java.lang")));
//        List<Book> books = new ArrayList<>();
//        books.add(Book.builder().name("book1").build());
//        books.add(Book.builder().name("book2").build());
//        log.info(JsonUtils.objectToJson(Person.builder().age(1).name("bookName").books(books).build()));
//        log.info(JsonUtils.parseObject(Person.builder().age(1).name("bookName").books(books).build()));
        String s = "";
        String s1 = stri(s);
//        int b = 5;
//        Person person = Person.builder().age(2).build();
//        Book book = Book.builder().build();
//        Book book1 = book;
//        boo(book1);
//
        log.info(String.valueOf(s.hashCode()) + " " + stri(s).hashCode());
//        log.info(String.valueOf(book));
//        log.info(String.valueOf(book1));
//        per(person);
//        log.info(String.valueOf(person));
    }

    static  String stri(String s) {
        return s.trim();
    }

    static void per(Person p) {
        p.setAge(1);
    }

    static void boo(Book book) {
        book.setBookNameee("test");
    }
}