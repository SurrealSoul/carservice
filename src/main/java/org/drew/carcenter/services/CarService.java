package org.drew.carcenter.services;

import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.CarDTO;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    Car addCar(CarDTO car) throws UserNotFoundException;

    void deleteCar(Long id) throws CarNotFoundException;

    Car updateCar(CarDTO car, Long id) throws CarNotFoundException, UserNotFoundException;

    List<Car> getCarsByUser(User user) throws UserNotFoundException;

    Car getCarById(Long id) throws CarNotFoundException;
}