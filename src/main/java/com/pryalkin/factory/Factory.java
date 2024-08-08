package com.pryalkin.factory;

import com.pryalkin.controller.impl.Controller;
import com.pryalkin.repository.impl.Repository;
import com.pryalkin.service.impl.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class Factory {

    private static List<com.pryalkin.service.Service> services = new ArrayList<>();
    private static List<com.pryalkin.repository.Repository> repositories = new ArrayList<>();
    private static List<com.pryalkin.controller.Controller> controllers = new ArrayList<>();
    private static int count = 0;

    private Factory(){

    }


    public static void init(){
        List<Class<?>> annotatedClassesController = null;
        List<Class<?>> annotatedClassesService = null;
        List<Class<?>> annotatedClassesRepository = null;
        try {
            annotatedClassesController = findAnnotatedClasses("com.pryalkin.controller.impl", com.pryalkin.annotation.Controller.class);
            annotatedClassesService = findAnnotatedClasses("com.pryalkin.service.impl", com.pryalkin.annotation.Service.class);
            annotatedClassesRepository = findAnnotatedClasses("com.pryalkin.repository.impl", com.pryalkin.annotation.Repository.class);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Найденные классы с аннотацией " + Controller.class.getSimpleName() + ":");
        System.out.println("Найденные классы с аннотацией " + Service.class.getSimpleName() + ":");
        System.out.println("Найденные классы с аннотацией " + Repository.class.getSimpleName() + ":");
        count = annotatedClassesController.size() + annotatedClassesService.size() + annotatedClassesRepository.size();
        while (count != 0){
            createObject(annotatedClassesController, com.pryalkin.annotation.Controller.class.getSimpleName());
            createObject(annotatedClassesService, com.pryalkin.annotation.Service.class.getSimpleName());
            createObject(annotatedClassesRepository, com.pryalkin.annotation.Repository.class.getSimpleName());
        }
    }

//    public static void init(){
//        Properties properties = new Properties();
//        try (InputStream in = Factory.class.getClassLoader().getResourceAsStream("app.properties")){
//            properties.load(in);
//            String serviceClass = properties.getProperty("service");
//            String repositoryClass = properties.getProperty("repository");
//            String controllerClass = properties.getProperty("controller");
//            repository = (Repository) Class.forName(repositoryClass).getConstructor().newInstance();
//            service = (Service) Class.forName(serviceClass)
//                    .getConstructor(Repository.class).newInstance(repository);
//            controller = (Controller) Class.forName(controllerClass)
//                    .getConstructor(Service.class).newInstance(service);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public com.pryalkin.service.Service getService(String className) {
        for (com.pryalkin.service.Service service : services) {
            if (service.getClass().getSimpleName().equals(className)) {
                return service;
            }
        }
        return null;
    }

    public com.pryalkin.repository.Repository getRepository(String className) {
        for (com.pryalkin.repository.Repository repository : repositories) {
            if (repository.getClass().getSimpleName().equals(className)) {
                return repository;
            }
        }
        return null;
    }

    public com.pryalkin.controller.Controller getController(String className) {
        for (com.pryalkin.controller.Controller controller : controllers) {
            if (controller.getClass().getSimpleName().equals(className)) {
                return controller;
            }
        }
        return null;
    }

    public static List<com.pryalkin.service.Service> getServices() {
        return services;
    }

    public static List<com.pryalkin.repository.Repository> getRepositories() {
        return repositories;
    }

    public static List<com.pryalkin.controller.Controller> getControllers() {
        return controllers;
    }

    private static void createObject(List<Class<?>> annotatedClasses, String simpleName) {
        for (Class<?> annotatedClass : annotatedClasses) {
            checkAndCreateObject(annotatedClass, simpleName);
        }

    }

    private static void checkAndCreateObject(Class<?> annotatedClass, String simpleName) {
        switch (simpleName){
            case "Service" -> {
                if(services.stream().noneMatch(service -> annotatedClass.isAssignableFrom(service.getClass())))
                    createService(annotatedClass);
            }
            case "Repository" -> {
                if(repositories.stream().noneMatch(repository -> annotatedClass.isAssignableFrom(repository.getClass())))
                    createRepository(annotatedClass);
            }
            case "Controller" -> {
                if(controllers.stream().noneMatch(controller -> annotatedClass.isAssignableFrom(controller.getClass())))
                    createController(annotatedClass);
            }
        }
    }

    private static void createController(Class<?> annotatedClass) {
        Constructor<?>[] constructors = annotatedClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Конструктор: " + constructor.getName());
            // Получаем параметры конструктора
            Parameter[] parameters = constructor.getParameters();
            if(parameters.length == 0){
                try {
                    com.pryalkin.controller.Controller controller = (com.pryalkin.controller.Controller) constructor.newInstance();
                    controllers.add(controller);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Выводим информацию о параметрах
                for (Parameter parameter : parameters) {
                    System.out.println("  - Параметр: " + parameter.getType().getSimpleName() + " " + parameter.getName());
                }
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().getFirst();
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().getFirst();
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().getFirst();
                    } else return;
                }
                try {
                    com.pryalkin.controller.Controller controller = (com.pryalkin.controller.Controller) constructor.newInstance(params);
                    controllers.add(controller);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void createRepository(Class<?> annotatedClass) {
        Constructor<?>[] constructors = annotatedClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Конструктор: " + constructor.getName());
            // Получаем параметры конструктора
            Parameter[] parameters = constructor.getParameters();
            if(parameters.length == 0){
                try {
                    com.pryalkin.repository.Repository repository = (com.pryalkin.repository.Repository) constructor.newInstance();
                    repositories.add(repository);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
//                 Выводим информацию о параметрах
                // Выводим информацию о параметрах
                for (Parameter parameter : parameters) {
                    System.out.println("  - Параметр: " + parameter.getType().getSimpleName() + " " + parameter.getName());
                }
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().getFirst();
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().getFirst();
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().getFirst();
                    } else return;
                }
                try {
                    System.out.println(params.toString());
                    com.pryalkin.repository.Repository repository = (com.pryalkin.repository.Repository) constructor.newInstance(params);
                    repositories.add(repository);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static void createService(Class<?> annotatedClass) {
        Constructor<?>[] constructors = annotatedClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Конструктор: " + constructor.getName());
            // Получаем параметры конструктора
            Parameter[] parameters = constructor.getParameters();
            if(parameters.length == 0){
                try {
                    com.pryalkin.service.Service service = (com.pryalkin.service.Service) constructor.newInstance();
                    services.add(service);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // Выводим информацию о параметрах
                for (Parameter parameter : parameters) {
                    System.out.println("  - Параметр: " + parameter.getType().getSimpleName() + " " + parameter.getName());
                }
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().getFirst();
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().getFirst();
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().getFirst();
                    } else return;
                }
                try {
                    System.out.println(params.toString());
                    com.pryalkin.service.Service service = (com.pryalkin.service.Service) constructor.newInstance(params);
                    services.add(service);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Class<?>> findAnnotatedClasses(String packageName, Class<? extends Annotation> annotationClass) throws ClassNotFoundException, IOException {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        // Получаем список файлов в пакете
        Enumeration<URL> resources = URLClassLoader.getSystemClassLoader().getResources(packageName.replace('.', '/'));
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            if (resource.getProtocol().equals("jar")) {
                // Если ресурс - JAR-файл, то сканируем его
                String jarPath = resource.getPath();
                jarPath = jarPath.substring(5, jarPath.indexOf("!"));
                JarFile jarFile = new JarFile(jarPath);
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    String entryName = entry.getName();
                    if (entryName.startsWith(packageName.replace('.', '/')) && entryName.endsWith(".class")) {
                        String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');
                        // Проверяем, имеет ли класс аннотацию
                        if (hasAnnotation(className, annotationClass)) {
                            annotatedClasses.add(Class.forName(className));
                        }
                    }
                }
                jarFile.close();
            } else {
                // Если ресурс - директория, то сканируем ее
                File directory = new File(resource.getFile());
                File[] files = directory.listFiles();
                if (files != null) {
                    for (File file : files) {
                        if (file.isFile() && file.getName().endsWith(".class")) {
                            String className = file.getAbsolutePath().replace(File.separatorChar, '.');
                            className = className.substring(0, className.length() - 6);
                            className = className.substring("D:.y_lab_4.CarShop-Service.target.classes.".length());
                            // Проверяем, имеет ли класс аннотацию
                            if (hasAnnotation(className, annotationClass)) {
                                annotatedClasses.add(Class.forName(className));
                            }
                        }
                    }
                }
            }
        }
        return annotatedClasses;
    }

    private static boolean hasAnnotation(String className, Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.isAnnotationPresent(annotationClass);
    }

}

