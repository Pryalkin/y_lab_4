import com.pryalkin.factory.Factory;
import com.pryalkin.model.User;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Testcontainers
public class ServiceImplTest {

    private static Connection conn;

    @Container
    static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4")
            .withDatabaseName("y_lab").withUsername("postgres").withPassword("root").withExposedPorts(5432);

    @BeforeAll
    static void runContainer(){
        postgres.start();
        String url = null;
        String username = null;
        String password = null;
        try(InputStream in = Factory.class.getClassLoader().getResourceAsStream("app.properties")){
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/y_lab", username, password);
            JdbcConnection jdbcConnection = new JdbcConnection(conn);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            Liquibase liquibase = new Liquibase("db/changelog/changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    static void stopContainer() {
        postgres.stop();
    }

//    @Test
//    void registration() {
//        User savedUser = new User();
//        savedUser.setUsername("testUser");
//        savedUser.setPassword("testPassword");
//        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO users (name, surname, username, password, role) values (?, ?, ?, ?, ?)")){
//            ps.setString(1, savedUser.getName());
//            ps.setString(2, savedUser.getSurname());
//            ps.setString(3, savedUser.getUsername());
//            ps.setString(4, savedUser.getPassword());
//            ps.setString(5, savedUser.getRole());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return user;
//
//        Repository repository = new RepositoryImpl();
//        Service service = new ServiceImpl(repository);
//
//        ;
//
//        User user = repository.saveUser(savedUser);
//        Assertions.assertEquals(savedUser.getUsername(), user.getUsername());
//        Assertions.assertEquals(savedUser.getPassword(), user.getPassword());
//
//    }

    @Test
    public void getAuthorization() {
        User wrongUser = new User();
        wrongUser.setUsername("pavel");
        wrongUser.setPassword("pavel");

        User user = new User();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?" )){
            ps.setString(1, wrongUser.getUsername());
            ps.setString(2, wrongUser.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(user.getUsername(), wrongUser.getUsername());
        Assertions.assertEquals(user.getPassword(), wrongUser.getPassword());
    }

    @Test
    public void getRegistrationClient() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM users")){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                User user = new User();
                user.setId(rs.getString(1));
                user.setName(rs.getString(2));
                user.setSurname(rs.getString(3));
                user.setUsername(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(users.stream().filter(user -> user.getRole().equals("CLIENT")).count(), 2);
    }
//
//    @Test
//    public void getRegistrationManager() {
//        Repository repository = new RepositoryImpl();
//        Service service = new ServiceImpl(repository);
//        Set<User> users = new HashSet<>();
//
//        User savedUser33 = new User();
//        savedUser33.setUsername("testUser33");
//        savedUser33.setPassword("testPassword33");
//        savedUser33.setRole(Role.MANAGER.name());
//        User user33 = repository.saveUser(savedUser33);
//        users.add(user33);
//
//        User savedUser44 = new User();
//        savedUser44.setUsername("testUser44");
//        savedUser44.setPassword("testPassword44");
//        savedUser44.setRole(Role.MANAGER.name());
//        User user44 = repository.saveUser(savedUser44);
//        users.add(user44);
//
//        Assertions.assertEquals(users, service.getRegistrationManager());
//    }
//
//    @Test
//    public void getUser() {
//        Repository repository = new RepositoryImpl();
//        Service service = new ServiceImpl(repository);
//
//        User savedUser = new User();
//        savedUser.setUsername("testUsername");
//        savedUser.setPassword("testPassword");
//        User regUser = repository.saveUser(savedUser);
//
//        User findUser = new User();
//        findUser.setUsername("testUsername");
//        findUser.setPassword("testPassword");
//
//        User checkUser = service.getUser(findUser);
//        Assertions.assertEquals(regUser, checkUser);
//    }
//
//    @Test
//    public void updateCar() {
//        Repository repository = new RepositoryImpl();
//        Service service = new ServiceImpl(repository);
//
//        Car createCar = new Car();
//        createCar.setBrand("BMW");
//        createCar.setModel("X7");
//        createCar.setYearOfIssue("2012");
//        createCar.setPrice("20500");
//        createCar.setState(State.NEW.name());
//        createCar.setInStock(InStock.TRUE.name());
//        Car savedCar = service.addCar(createCar);
//
//        Car updateCar = new Car();
//        updateCar.setId(savedCar.getId());
//        updateCar.setModel("X12");
//        updateCar.setYearOfIssue("2020");
//
//        Car savedUpCar = service.updateCar(updateCar);
//
//        Car checkUser = new Car();
//        checkUser.setId(savedCar.getId());
//        checkUser.setBrand(savedCar.getBrand());
//        checkUser.setModel(updateCar.getModel());
//        checkUser.setYearOfIssue(updateCar.getYearOfIssue());
//        checkUser.setPrice(savedCar.getPrice());
//        checkUser.setState(savedCar.getState());
//        checkUser.setInStock(savedCar.getInStock());
//
//        System.out.println(savedUpCar);
//        System.out.println(checkUser);
//        Assertions.assertEquals(savedUpCar, checkUser);
//    }
//
//    @Test
//    public void findCarBrandModel() {
//        Repository repository = new RepositoryImpl();
//        Service service = new ServiceImpl(repository);
//
//        Car createCar = new Car();
//        createCar.setBrand("BMW");
//        createCar.setModel("X7");
//        createCar.setYearOfIssue("2012");
//        createCar.setPrice("20500");
//        createCar.setState(State.NEW.name());
//        createCar.setInStock(InStock.TRUE.name());
//        Car savedCar = service.addCar(createCar);
//        List<Car> savedCars = service.getCars();
//
//        Car findCar = new Car();
//        findCar.setBrand("BMW");
//        findCar.setModel("X7");
//
//        List<Car> cars = service.findCarBrandModel(findCar);
//
//        Assertions.assertEquals(savedCars, cars);
//
//    }
}
