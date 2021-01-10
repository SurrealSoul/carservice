package org.drew.carcenter.services;

import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.repos.CarRepository;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class CarServiceTest {
    public User drew;
    @Autowired
    private CarService carService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CarRepository carRepository;

    @Before
    public void setUp() {
        drew = new User();
        drew.setId(10L);
        drew.setPhone("5133285317");
        drew.setLastName("davis");
        drew.setFirstName("drew");
        drew.setEmail("andrewcdavis0@gmail.com");
        drew.setUsername("andrewcdavis0");

        Mockito.when(userRepository.findUserById(10L))
                .thenReturn(drew);

        Car car = new Car();
        car.setYear("1991");
        car.setModel("toyota");
        car.setUser(drew);
        car.setModel("landcruiser");

        Car car2 = new Car();
        car2.setYear("1995");
        car2.setModel("car");
        car2.setUser(drew);
        car2.setModel("madza");

        Car car3 = new Car();
        car3.setYear("2000");
        car3.setModel("fast");
        car3.setUser(drew);
        car3.setModel("porche");

        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car2);
        carList.add(car3);

        Mockito.when(carRepository.findCarById(10L)).thenReturn(car);
        Mockito.when(carRepository.findCarsByUser(drew)).thenReturn(carList);
    }

    @Test
    public void whenGetCarsByUser_thenDoNotThrowException() {
        assertDoesNotThrow(() -> carService.getCarsByUser(drew));
    }

    @Test
    public void whenDeleteCarThatDoesNotExist_thenThrowException() {

        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () -> carService.deleteCar(5L),
                "Expecting userService to throw an exception");

        assertTrue(thrown.getMessage().equals("car not found"));
    }

    @TestConfiguration
    static class CarServiceImplTestContextConfiguration {
        @Bean
        public CarService carService() {
            return new CarServiceImpl();
        }
    }
}
