package org.drew.carcenter.api;

import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.CarDTO;
import org.drew.carcenter.data.models.dto.UserDTO;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserExistException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.drew.carcenter.services.CarService;
import org.drew.carcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller
public class CarController
{
    private final CarService carService;

    @Autowired
    public CarController(CarService carService)
    {
        this.carService = carService;
    }

    @PostMapping("/cars")
    public ResponseEntity<String> createCar(@RequestBody CarDTO params)
    {
        try
        {
            Car car = carService.addCar(params);
            return new ResponseEntity<>(car.toString(), HttpStatus.OK);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/cars/{carId}")
    public ResponseEntity<String> updateCar(@RequestBody CarDTO params, @PathVariable long carId)
    {
        try
        {
            Car updatedCar = carService.updateCar(params, carId);
            return new ResponseEntity<>(updatedCar.toString(), HttpStatus.OK);
        }
        catch (CarNotFoundException e)
        {
            return new ResponseEntity<>("Car not found", HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/cars/{carId}")
    public ResponseEntity<String> deleteCar(@PathVariable long carId)
    {
        try
        {
            carService.deleteCar(carId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (CarNotFoundException e)
        {
            return new ResponseEntity<>("car not found", HttpStatus.NOT_FOUND);
        }
    }
}
