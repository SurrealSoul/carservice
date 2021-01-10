package org.drew.carcenter.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.dto.CarDTO;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.drew.carcenter.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller
/**
 * The rest controller for modifying cars
 */
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO params) throws JsonProcessingException {
        try {
            Car car = carService.addCar(params);
            return new ResponseEntity<>(CarDTO.fromEntity(car), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<CarDTO> updateCar(@RequestBody CarDTO params, @PathVariable long carId) throws JsonProcessingException {
        try {
            Car updatedCar = carService.updateCar(params, carId);
            return new ResponseEntity<>(CarDTO.fromEntity(updatedCar), HttpStatus.OK);
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car not found");
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable long carId) {
        try {
            Car car = carService.getCarById(carId);
            return new ResponseEntity<>(CarDTO.fromEntity(car), HttpStatus.OK);

        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car not found");
        }
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable long carId) {
        try {
            carService.deleteCar(carId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (CarNotFoundException e) {
            return new ResponseEntity<>("car not found", HttpStatus.NOT_FOUND);
        }
    }
}
