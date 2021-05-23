package com.sazonov.ioc;

import com.sazonov.CustomHttpServlet;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

@Slf4j
public class AppContextInitializer {
    public static AppContextInitializer INSTANCE = new AppContextInitializer();

    @Getter
    private Set<Class<?>> allProjectClasses = new HashSet<>();
    private Set<Class<?>> components = new HashSet<>();

    private AppContextInitializer() {
    }

    @SneakyThrows
    public void init(String packageName) {
        AppContextInitializer appContextInitializer = INSTANCE;
        Class<? extends AppContextInitializer> appContextInitializerClass = INSTANCE.getClass();
        URL url = appContextInitializerClass.getClassLoader().getResource(getFilePathOfPackageName(packageName));
        assert Objects.nonNull(url);

        this.allProjectClasses = appContextInitializer.findClasses(new File(url.getFile()), packageName);
        this.components = findComponents();
        instantiateContextClassesInstances();

//        log.info(String.valueOf(allProjectClasses));
        log.info("Components {}", components);
    }

    public void handleHttpRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Class<?>> controllers = new ArrayList<>();
        Class<?> suitableController = null;
        String requestingPath = req.getPathInfo();
        log.info("request path {} {} {}", requestingPath, allProjectClasses.size(), req.getMethod());
        Controller controllerAnnotation;
        for (Class<?> clazz : allProjectClasses) {
            controllerAnnotation = clazz.getAnnotation(Controller.class);
            if (Objects.nonNull(controllerAnnotation)) {
                log.info("CONTROLLER {} {}", requestingPath, controllerAnnotation.uriPath());
                if (requestingPath.contains(controllerAnnotation.uriPath())) {
                    suitableController = clazz;
                    break;
                }
            }
        }

        if (Objects.isNull(suitableController)) {
            throw new Exception("No found suitable controller for this requesting path " + requestingPath);
        }

        Method[] suitableControllerMethods = suitableController.getDeclaredMethods();
        if (suitableControllerMethods.length <= 0) {
            throw new Exception("No found suitable methods of controller for this requesting path " + requestingPath);
        }

        for (Method method : suitableControllerMethods) {
            Annotation[] annotations = method.getAnnotations();
            if (annotations.length > 0) {
                for (Annotation annotation : annotations) {
//                    annotation.getClass().getAnnotation();
                }
            }
        }
    }

    /**
     * Finding all beans annotated with @Component
     *
     * @return
     */
    private Set<Class<?>> findComponents() {
        Set<Class<?>> components = new HashSet<>();
        this.allProjectClasses.forEach(clazz -> {
            if(Objects.nonNull(clazz.getAnnotation(Component.class))) {
                components.add(clazz);
            }
        });
        return components;
    }

    private boolean isClassAComponent(Class<?> clazz) {
        if (Objects.nonNull(clazz)) {
            clazz.getAnnotation(Component.class);
        }
        return false;
    }

    private Set<Class<?>> findClasses(File dir, String packageName) {
        Set<Class<?>> classes = new HashSet<>();
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach(item -> {
            try {
                if (item.isDirectory()) {
                    classes.addAll(findClasses(item, String.format("%s.%s", packageName, item.getName())));
                } else {
                    log.info("Found class:: {}", String.format("%s.%s", packageName, getClassName(item.getName())));
                    classes.add(Class.forName(String.format("%s.%s", packageName, getClassName(item.getName()))));
                }
            } catch (ClassNotFoundException classNotFoundException) {
                log.error(String.format("Cannot find class for classpath %s", String.format("%s.%s", packageName, getClassName(item.getName()))));
            }
        });
        return classes;
    }

    private void instantiateContextClassesInstances() throws Exception {
        Set<Object> instances = new HashSet<>();
        this.components.forEach(clazz -> {
            try {
                getConstructorParameters(clazz);
                if (!clazz.isAnnotation()) {
                    instances.add(clazz.newInstance());
                }
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }

    private void getConstructorParameters(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] paramTypes = constructor.getParameterTypes();
            for (Class<?> paramType : paramTypes) {
//                log.info("{} {}", paramType.getC);
                System.out.print(paramType.getName() + " ");
            }
            System.out.println();
        }
    }

    private boolean isClassInstanceExists(Class<?> clazz) {
        return true;
    }

    private String getClassName(String fullClassName) {
        return fullClassName.replace(".class", "");
    }

    private String getFilePathOfPackageName(String packageName) {
        return packageName.replace(".", "/");
    }
}
