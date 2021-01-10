package org.drew.carcenter.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.Appointment;
import org.drew.carcenter.data.models.dto.AppointmentDTO;
import org.drew.carcenter.exceptions.AppointmentNotFoundException;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;
import org.drew.carcenter.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller
/**
 * The rest controller for modifying appointments
 */
public class AppointmentController {
    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO params) {
        try {
            Appointment appointment = appointmentService.addAppointment(params);
            return new ResponseEntity<>(AppointmentDTO.fromEntity(appointment), HttpStatus.OK);
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car not found");
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        }
    }

    @PostMapping("/appointments/randomAppointment")
    public ResponseEntity<AppointmentDTO> createRandomAppointment() {
        Appointment appointment = appointmentService.addRandomAppointment();

        return new ResponseEntity<>(AppointmentDTO.fromEntity(appointment), HttpStatus.OK);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO params, @PathVariable long appointmentId) throws JsonProcessingException {
        try {
            Appointment appointment = appointmentService.updateAppointment(params, appointmentId);
            return new ResponseEntity<>(AppointmentDTO.fromEntity(appointment), HttpStatus.OK);
        } catch (CarNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "car not found");
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        } catch (AppointmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "appointment not found");
        }
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable long appointmentId) throws JsonProcessingException {
        try {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            return new ResponseEntity<>(AppointmentDTO.fromEntity(appointment), HttpStatus.OK);
        } catch (AppointmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "appointment not found");
        }
    }

    @GetMapping("/appointments/{startDate}/{endDate}")
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDateRange(@PathVariable String startDate, @PathVariable String endDate) throws JsonProcessingException {
        try {
            List<Appointment> appointmentList = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
            List<AppointmentDTO> dtos = new ArrayList<>();
            // convert each appointment to the response dto
            for (Appointment a : appointmentList) {
                dtos.add(AppointmentDTO.fromEntity(a));
            }
            return new ResponseEntity<>(dtos, HttpStatus.OK);
        } catch (AppointmentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "appointment not found for date range");
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "date time format error, please use yyyy-MM-dd hh:mm:ss");
        }
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable long appointmentId) {
        try {
            appointmentService.deleteAppointment(appointmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AppointmentNotFoundException e) {
            return new ResponseEntity<>("appointment not found", HttpStatus.NOT_FOUND);
        }
    }
}
