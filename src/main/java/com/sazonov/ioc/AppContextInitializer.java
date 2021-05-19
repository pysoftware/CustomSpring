package com.sazonov.ioc;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

@Slf4j
public class AppContextInitializer {
    private static AppContextInitializer instance;

    @Getter
    private List<Class<?>> allProjectClasses = new ArrayList<>();

    private AppContextInitializer() {
    }

    public static AppContextInitializer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AppContextInitializer();
        }
        return instance;
    }

    @SneakyThrows
    public void init(String packageName) {
        AppContextInitializer appContextInitializer = getInstance();
        Class<? extends AppContextInitializer> appContextInitializerClass = getInstance().getClass();
        URL url = appContextInitializerClass.getClassLoader().getResource("com/sazonov");
        assert Objects.nonNull(url);

        this.allProjectClasses = appContextInitializer.findClasses(new File(url.getFile()), packageName);

        log.info(String.valueOf(allProjectClasses));
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
                for (Annotation annotation: annotations) {
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
    private List<Class<?>> findBeans() {
        return null;
    }

    private List<Class<?>> findClasses(File dir, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
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

    private String getClassName(String fullClassName) {
        return fullClassName.replace(".class", "");
    }
}
