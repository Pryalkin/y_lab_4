package com.pryalkin.factory;

import com.pryalkin.repository.Repository;
import com.pryalkin.service.Service;
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
import java.util.List;
import java.util.Properties;

public class Factory {

    private static Factory factory;

    private static List<Service> services = new ArrayList<>();
    private static List<Repository> repositories = new ArrayList<>();
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

    private Factory() throws SQLException, IOException, ClassNotFoundException {
        init();
    }

    public static Factory initialization() throws SQLException, IOException, ClassNotFoundException {
        if (factory == null){
            factory = new Factory();
        }
        return factory;
    }

    private static void createOrDropTables() throws SQLException, FileNotFoundException {
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

//    public static List<Class<?>> findClassesInFolder(String folderPath) throws ClassNotFoundException, IOException {
//        List<Class<?>> classes = new ArrayList<>();
//        File folder = new File(folderPath);
//        if (!folder.exists() || !folder.isDirectory()) {
//            return classes; // Папка не существует или не является директорией
//        }
//
//        // Создаем URLClassLoader для загрузки классов из папки
//        URLClassLoader classLoader = new URLClassLoader(new URL[] {folder.toURI().toURL()}, Factory.class.getClassLoader());
//
//        // Перебираем все файлы в папке
//        for (File file : folder.listFiles()) {
//            if (file.isFile() && file.getName().endsWith(".class")) {
//                String className = file.getName().substring(0, file.getName().length() - 6); // Убираем ".class"
//                // Полное имя класса (с пакетом)
//                String classFullName = folderPath.replace(File.separatorChar, '.') + '.' + className;
//
//                // Загружаем класс и добавляем в список
//                classes.add(Class.forName(classFullName, true, classLoader));
//            }
//        }
//
//        return classes;
//    }


    private static void init() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("INIT FACTORY");
        createOrDropTables();

//        String folderPath = "D:\\BSUIR\\Java\\web-y-lab\\y_lab_4\\CarShop\\Web\\build\\classes\\java\\main\\com\\pryalkin\\controller\\impl"; // Замените на реальный путь
//        List<Class<?>> classes = Factory.findClassesInFolder(folderPath);
//
//        // Вывод списка классов
//        for (Class<?> clazz : classes) {
//            System.out.println(clazz.getName());
//        }


        List<Class<?>> annotatedClassesController = null;
        List<Class<?>> annotatedClassesService = null;
        List<Class<?>> annotatedClassesRepository = null;
        try {
            annotatedClassesService = findAnnotatedClasses("D:\\project\\y_lab_4\\CarShop\\Web\\build\\classes\\java\\main\\com\\pryalkin\\service\\impl", com.pryalkin.annotation.Service.class);
            annotatedClassesRepository = findAnnotatedClasses("D:\\project\\y_lab_4\\CarShop\\Web\\build\\classes\\java\\main\\com\\pryalkin\\repository\\impl", com.pryalkin.annotation.Repository.class);
        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
        count = annotatedClassesService.size() + annotatedClassesRepository.size();
        while (count != 0){
            createObject(annotatedClassesService, com.pryalkin.annotation.Service.class.getSimpleName());
            createObject(annotatedClassesRepository, com.pryalkin.annotation.Repository.class.getSimpleName());
        }
    }

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

    public List<Service> getServices() {
        return services;
    }

    public List<Repository> getRepositories() {
        return repositories;
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
        }
    }

    private static void createRepository(Class<?> annotatedClass) {
        Constructor<?>[] constructors = annotatedClass.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            // Получаем параметры конструктора
            Parameter[] parameters = constructor.getParameters();
            if(parameters.length == 0){
                try {
                    Repository repository = (Repository) constructor.newInstance();
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
                    if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().get(0);
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().get(0);
                    } else return;
                }
                try {
                    Repository repository = (Repository) constructor.newInstance(params);
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
                    Service service = (Service) constructor.newInstance();
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
                    if(services.stream().anyMatch(service -> parameter.getType().isAssignableFrom(service.getClass()))){
                        params[i] = services.stream().filter(service -> parameter.getType().isAssignableFrom(service.getClass())).toList().get(0);
                    } else if(repositories.stream().anyMatch(repository -> parameter.getType().isAssignableFrom(repository.getClass()))){
                        params[i] = repositories.stream().filter(repository -> parameter.getType().isAssignableFrom(repository.getClass())).toList().get(0);
                    } else return;
                }
                try {
                    Service service = (Service) constructor.newInstance(params);
                    services.add(service);
                    count--;
                } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static List<Class<?>> findAnnotatedClasses(String folderPath, Class<? extends Annotation> annotationClass) throws ClassNotFoundException, IOException {
//        List<Class<?>> annotatedClasses = new ArrayList<>();
//        // Получаем список файлов в пакете
//        File folder = new File(packageName);
//        URLClassLoader classLoader = new URLClassLoader(new URL[] {folder.toURI().toURL()}, Factory.class.getClassLoader());

        List<Class<?>> classes = new ArrayList<>();
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return classes; // Папка не существует или не является директорией
        }

        // Создаем URLClassLoader для загрузки классов из папки
        URLClassLoader classLoader = new URLClassLoader(new URL[] {folder.toURI().toURL()}, Factory.class.getClassLoader());

        // Перебираем все файлы в папке
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String className = file.getName().substring(0, file.getName().length() - 6);
                // Полное имя класса (с пакетом)
                String classFullName = folderPath.replace(File.separatorChar, '.') + '.' + className;
                int index = classFullName.indexOf("com.pryalkin.");
                classFullName = classFullName.substring(index);
                if (hasAnnotation(classFullName, annotationClass)) {
                    classes.add(Class.forName(classFullName));
                }
            }
        }
        return classes;
    }






//        Enumeration<URL> resources = classLoader.getResources(packageName);
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            if (resource.getProtocol().equals("jar")) {
//                // Если ресурс - JAR-файл, то сканируем его
//                String jarPath = resource.getPath();
//                jarPath = jarPath.substring(5, jarPath.indexOf("!"));
//                JarFile jarFile = new JarFile(jarPath);
//                Enumeration<JarEntry> entries = jarFile.entries();
//                while (entries.hasMoreElements()) {
//                    JarEntry entry = entries.nextElement();
//                    String entryName = entry.getName();
//                    if (entryName.startsWith(packageName.replace('.', '/')) && entryName.endsWith(".class")) {
//                        String className = entryName.substring(0, entryName.length() - 6).replace('/', '.');
//                        // Проверяем, имеет ли класс аннотацию
//                        if (hasAnnotation(className, annotationClass)) {
//                            annotatedClasses.add(Class.forName(className));
//                        }
//                    }
//                }
//                jarFile.close();
//            } else {
//                // Если ресурс - директория, то сканируем ее
//                File directory = new File(resource.getFile());
//                File[] files = directory.listFiles();
//                if (files != null) {
//                    for (File file : files) {
//                        if (file.isFile() && file.getName().endsWith(".class")) {
//                            String className = file.getAbsolutePath().replace(File.separatorChar, '.');
//                            className = className.substring(0, className.length() - 6);
//                            int index = className.indexOf("com.pryalkin.");
//                            className = className.substring(index);
//                            System.out.println(className);
//                            // Проверяем, имеет ли класс аннотацию
//                            if (hasAnnotation(className, annotationClass)) {
//                                annotatedClasses.add(Class.forName(className));
//                            }
//                        }
//                    }
//                }
//            }
//        }


    private static boolean hasAnnotation(String className, Class<? extends Annotation> annotationClass) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);
        return clazz.isAnnotationPresent(annotationClass);
    }

}

