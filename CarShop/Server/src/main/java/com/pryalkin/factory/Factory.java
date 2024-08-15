package com.pryalkin.factory;

import com.pryalkin.annotation.Controller;
import com.pryalkin.annotation.Repository;
import com.pryalkin.annotation.Service;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Factory {

    private static List<com.pryalkin.service.Service> services = new ArrayList<>();
    private static List<com.pryalkin.repository.Repository> repositories = new ArrayList<>();
    private static List<com.pryalkin.controller.Controller> controllers = new ArrayList<>();
    private static int count = 0;

    private static String ddlAuto;
    private static Connection conn;

    static {
        String url = null;
        String username = null;
        String password = null;

        try(InputStream in = Factory.class.getClassLoader().getResourceAsStream("app.properties")){
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            ddlAuto = properties.getProperty("ddl-auto");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Factory(){

    }

    private static void createOrDropTables() throws SQLException, FileNotFoundException {
//        System.out.println("createOrDropTables");
//        if (ddlAuto.equals("drop")){
//            ScriptRunner sr = new ScriptRunner(conn);
//            Reader reader = new BufferedReader(new FileReader("src/main/resources/drop_tables.sql"));
//            sr.runScript(reader);
//            System.out.println("Таблицы успешно удалены!");
//        } else if (ddlAuto.equals("create")){
//            ScriptRunner sr = new ScriptRunner(conn);
//            Reader reader = new BufferedReader(new FileReader("src/main/resources/create_tables.sql"));
//            sr.runScript(reader);
//            System.out.println("Таблицы успешно созданы!");
//        } else System.out.println("Никаких обновлений не произошло!");
//        conn.close();
        System.out.println("JdbcConnection");
        try {
            JdbcConnection jdbcConnection = new JdbcConnection(conn);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase("changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() throws SQLException, FileNotFoundException {
        createOrDropTables();
        List<Class<?>> annotatedClassesController = null;
        List<Class<?>> annotatedClassesService = null;
        List<Class<?>> annotatedClassesRepository = null;
        try {
            System.out.println(Controller.class.getPackageName());
            annotatedClassesController = findAnnotatedClasses("com.pryalkin.controller.impl", com.pryalkin.annotation.Controller.class);
            annotatedClassesService = findAnnotatedClasses("com.pryalkin.service.impl", com.pryalkin.annotation.Service.class);
            annotatedClassesRepository = findAnnotatedClasses("com.pryalkin.repository.impl", com.pryalkin.annotation.Repository.class);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        count = annotatedClassesController.size() + annotatedClassesService.size() + annotatedClassesRepository.size();
        while (count != 0){
            createObject(annotatedClassesController, com.pryalkin.annotation.Controller.class.getSimpleName());
            createObject(annotatedClassesService, com.pryalkin.annotation.Service.class.getSimpleName());
            createObject(annotatedClassesRepository, com.pryalkin.annotation.Repository.class.getSimpleName());
        }
        System.out.println(services);
        System.out.println(controllers);
        System.out.println(repositories);
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

    public static com.pryalkin.service.Service getService(String className) {
        for (com.pryalkin.service.Service service : services) {
            if (service.getClass().getSimpleName().equals(className)) {
                return service;
            }
        }
        return null;
    }

    public static com.pryalkin.repository.Repository getRepository(String className) {
        for (com.pryalkin.repository.Repository repository : repositories) {
            if (repository.getClass().getSimpleName().equals(className)) {
                return repository;
            }
        }
        return null;
    }

    public static com.pryalkin.controller.Controller getController(String className) {
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
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().get(0);
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().get(0);
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().get(0);
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
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().get(0);
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().get(0);
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().get(0);
                    } else return;
                }
                try {
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
                int countParam = parameters.length;
                Object[] params = new Object[countParam];
                for (int i = 0; i < countParam; i++) {
                    Parameter parameter = parameters[i];
                    if(controllers.stream().anyMatch(controller -> parameter.getType().isAssignableFrom(controller.getClass()))){
                        params[i] = controllers.stream().filter(controller -> parameter.getType().isAssignableFrom(controller.getClass())).toList().get(0);
                    } else if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().get(0);
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().get(0);
                    } else return;
                }
                try {
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
                            int index = className.indexOf("com.pryalkin.");
                            className = className.substring(index);
                            System.out.println(className);
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

