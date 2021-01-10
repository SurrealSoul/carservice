package org.drew.carcenter.data.repos;


import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@DataJpaTest
public class CarRepositoryTest {
    @Autowired
    CarRepository carRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenFindByUser_thenReturnAllCars() {
        // given
        User user = new User();
        user.setEmail("user@drew.com");
        user.setUsername("user");
        user.setFirstName("user");
        user.setLastName("user");
        entityManager.persist(user);

        User user2 = new User();
        user2.setEmail("user2@drew.com");
        user2.setUsername("user2");
        user2.setFirstName("user2");
        user2.setLastName("user2");
        entityManager.persist(user2);

        Car car = new Car();
        car.setMake("make");
        car.setModel("model");
        car.setModel("1991");
        car.setUser(user);
        entityManager.persist(car);

        Car car2 = new Car();
        car2.setMake("make");
        car2.setModel("model");
        car2.setModel("1991");
        car2.setUser(user);
        entityManager.persist(car2);

        Car car3 = new Car();
        car3.setMake("make");
        car3.setModel("model");
        car3.setModel("1991");
        car3.setUser(user2);
        entityManager.persist(car3);
        entityManager.flush();

        // when
        List<Car> userCarList = carRepository.findCarsByUser(user);
        List<Car> user2CarList = carRepository.findCarsByUser(user2);

        // then
        assertThat(userCarList.size()).isEqualTo(2);
        assertThat(userCarList.get(0)).isEqualTo(car);
        assertThat(userCarList.get(1)).isEqualTo(car2);
        assertThat(user2CarList.size()).isEqualTo(1);
        assertThat(user2CarList.get(0)).isEqualTo(car3);

    }
}
