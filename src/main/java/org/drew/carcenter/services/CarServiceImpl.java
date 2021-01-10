package org.drew.carcenter.services;

import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.CarDTO;
import org.drew.carcenter.data.repos.CarRepository;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Car addCar(CarDTO car) throws UserNotFoundException {
        // check if the car is assigned to an existing user
        if (userRepository.findById(car.getUserId()).isPresent()) {
            Car newCar = Car.builder()
                    .make(car.getMake())
                    .model(car.getModel())
                    .year(car.getYear())
                    .user(userRepository.findById(car.getUserId()).get())
                    .build();
            return carRepository.save(newCar);
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    @Override
    public void deleteCar(Long id) throws CarNotFoundException {
        // check if the car exists before deleting
        if (carRepository.findById(id).isPresent()) {
            carRepository.deleteById(id);
        } else {
            throw new CarNotFoundException("car not found");
        }
    }

    @Override
    public Car updateCar(CarDTO car, Long id) throws CarNotFoundException, UserNotFoundException {
        // check if the car exists
        if (carRepository.findById(id).isPresent()) {
            // check if the user exists
            if (userRepository.findById(car.getUserId()).isPresent()) {
                Car newCar = Car.builder()
                        .id(id)
                        .make(car.getMake())
                        .model(car.getModel())
                        .year(car.getYear())
                        .user(userRepository.findById(car.getUserId()).get())
                        .build();
                return carRepository.save(newCar);
            } else {
                throw new UserNotFoundException("user not found");
            }
        } else {
            throw new CarNotFoundException("car not found");
        }
    }

    @Override
    public List<Car> getCarsByUser(User user) throws UserNotFoundException {
        if (userRepository.findUserById(user.getId()) != null) {
            return carRepository.findCarsByUser(user);
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    @Override
    public Car getCarById(Long id) throws CarNotFoundException {
        Car car = carRepository.findCarById(id);

        if (car != null) {
            return car;
        } else {
            throw new CarNotFoundException("car not found");
        }
    }
}
