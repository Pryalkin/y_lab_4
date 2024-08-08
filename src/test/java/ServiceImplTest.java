import com.pryalkin.emun.InStock;
import com.pryalkin.emun.Role;
import com.pryalkin.emun.State;
import com.pryalkin.model.Car;
import com.pryalkin.model.User;
import com.pryalkin.repository.impl.Repository;
import com.pryalkin.repository.impl.RepositoryImpl;
import com.pryalkin.service.impl.Service;
import com.pryalkin.service.impl.ServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceImplTest {

    @Test
    void registration() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);

        User savedUser = new User();
        savedUser.setUsername("testUser");
        savedUser.setPassword("testPassword");

        User user = repository.saveUser(savedUser);
        Assertions.assertEquals(savedUser.getUsername(), user.getUsername());
        Assertions.assertEquals(savedUser.getPassword(), user.getPassword());

    }

    @Test
    public void getAuthorization() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);

        User correctUser = new User();
        correctUser.setUsername("correctUser");
        correctUser.setPassword("correctUser");

        User wrongUser = new User();
        wrongUser.setUsername("wrongUser ");
        wrongUser.setPassword("wrongUser ");

        service.registration(correctUser);
        Assertions.assertEquals(service.getAuthorization(correctUser),
                service.encrypt(correctUser.getUsername() + "." + correctUser.getPassword(), 1));

        Assertions.assertEquals(service.getAuthorization(wrongUser), Boolean.FALSE.toString());
    }

    @Test
    public void getRegistrationClient() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);
        Set<User> users = new HashSet<>();

        User savedUser33 = new User();
        savedUser33.setUsername("testUser33");
        savedUser33.setPassword("testPassword33");
        savedUser33.setRole(Role.CLIENT.name());
        User user33 = repository.saveUser(savedUser33);
        users.add(user33);

        User savedUser44 = new User();
        savedUser44.setUsername("testUser44");
        savedUser44.setPassword("testPassword44");
        savedUser44.setRole(Role.CLIENT.name());
        User user44 = repository.saveUser(savedUser44);
        users.add(user44);

        Assertions.assertEquals(users, service.getRegistrationClient());
    }

    @Test
    public void getRegistrationManager() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);
        Set<User> users = new HashSet<>();

        User savedUser33 = new User();
        savedUser33.setUsername("testUser33");
        savedUser33.setPassword("testPassword33");
        savedUser33.setRole(Role.MANAGER.name());
        User user33 = repository.saveUser(savedUser33);
        users.add(user33);

        User savedUser44 = new User();
        savedUser44.setUsername("testUser44");
        savedUser44.setPassword("testPassword44");
        savedUser44.setRole(Role.MANAGER.name());
        User user44 = repository.saveUser(savedUser44);
        users.add(user44);

        Assertions.assertEquals(users, service.getRegistrationManager());
    }

    @Test
    public void getUser() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);

        User savedUser = new User();
        savedUser.setUsername("testUsername");
        savedUser.setPassword("testPassword");
        User regUser = repository.saveUser(savedUser);

        User findUser = new User();
        findUser.setUsername("testUsername");
        findUser.setPassword("testPassword");

        User checkUser = service.getUser(findUser);
        Assertions.assertEquals(regUser, checkUser);
    }

    @Test
    public void updateCar() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);

        Car createCar = new Car();
        createCar.setBrand("BMW");
        createCar.setModel("X7");
        createCar.setYearOfIssue("2012");
        createCar.setPrice("20500");
        createCar.setState(State.NEW.name());
        createCar.setInStock(InStock.TRUE.name());
        Car savedCar = service.addCar(createCar);

        Car updateCar = new Car();
        updateCar.setId(savedCar.getId());
        updateCar.setModel("X12");
        updateCar.setYearOfIssue("2020");

        Car savedUpCar = service.updateCar(updateCar);

        Car checkUser = new Car();
        checkUser.setId(savedCar.getId());
        checkUser.setBrand(savedCar.getBrand());
        checkUser.setModel(updateCar.getModel());
        checkUser.setYearOfIssue(updateCar.getYearOfIssue());
        checkUser.setPrice(savedCar.getPrice());
        checkUser.setState(savedCar.getState());
        checkUser.setInStock(savedCar.getInStock());

        System.out.println(savedUpCar);
        System.out.println(checkUser);
        Assertions.assertEquals(savedUpCar, checkUser);
    }

    @Test
    public void findCarBrandModel() {
        Repository repository = new RepositoryImpl();
        Service service = new ServiceImpl(repository);

        Car createCar = new Car();
        createCar.setBrand("BMW");
        createCar.setModel("X7");
        createCar.setYearOfIssue("2012");
        createCar.setPrice("20500");
        createCar.setState(State.NEW.name());
        createCar.setInStock(InStock.TRUE.name());
        Car savedCar = service.addCar(createCar);
        List<Car> savedCars = service.getCars();

        Car findCar = new Car();
        findCar.setBrand("BMW");
        findCar.setModel("X7");

        List<Car> cars = service.findCarBrandModel(findCar);

        Assertions.assertEquals(savedCars, cars);

    }
}
