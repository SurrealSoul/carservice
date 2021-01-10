package org.drew.carcenter.data.repos;

import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {
    Car save(Car car);

    void deleteById(Long id);

    List<Car> findCarsByUser(User user);

    Car findCarById(Long id);
}
