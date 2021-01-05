package org.drew.carcenter.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
@Controller
public class AppointmentController
{
    private final AppointmentService appointmentService;
    private final ObjectMapper objectMapper;

    @Autowired
    public AppointmentController(AppointmentService appointmentService)
    {
        this.appointmentService = appointmentService;
        objectMapper = new ObjectMapper();
    }

    @PostMapping("/appointments")
    public ResponseEntity<String> createAppointment(@RequestBody AppointmentDTO params) throws JsonProcessingException
    {
        try
        {
            Appointment appointment = appointmentService.addAppointment(params);
            return new ResponseEntity<>(objectMapper.writeValueAsString(AppointmentDTO.fromEntity(appointment)), HttpStatus.OK);
        }
        catch (CarNotFoundException e)
        {
            return new ResponseEntity<>("car not found", HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/appointments/randomAppointment")
    /**
     * Creates an appointment at a random time with a new user and car
     */
    public ResponseEntity<String> createRandomAppointment() throws JsonProcessingException
    {
        Appointment appointment = appointmentService.addRandomAppointment();

        return new ResponseEntity<>(objectMapper.writeValueAsString(AppointmentDTO.fromEntity(appointment)), HttpStatus.OK);
    }

    @PutMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> updateAppointment(@RequestBody AppointmentDTO params, @PathVariable long appointmentId) throws JsonProcessingException
    {
        try
        {
            Appointment appointment = appointmentService.updateAppointment(params, appointmentId);
            return new ResponseEntity<>(objectMapper.writeValueAsString(AppointmentDTO.fromEntity(appointment)), HttpStatus.OK);
        }
        catch (CarNotFoundException e)
        {
            return new ResponseEntity<>("car not found", HttpStatus.NOT_FOUND);
        }
        catch (UserNotFoundException e)
        {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
        catch (AppointmentNotFoundException e)
        {
            return new ResponseEntity<>("appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> getAppointmentById(@PathVariable long appointmentId) throws JsonProcessingException
    {
        try
        {
            Appointment appointment = appointmentService.getAppointmentById(appointmentId);
            return new ResponseEntity<>(objectMapper.writeValueAsString(AppointmentDTO.fromEntity(appointment)), HttpStatus.OK);
        }
        catch (AppointmentNotFoundException e)
        {
            return new ResponseEntity<>("appointment not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointments/{startDate}/{endDate}")
    public ResponseEntity<String> getAppointmentsByDateRange(@PathVariable String startDate, @PathVariable String endDate) throws JsonProcessingException
    {
        try
        {
            List<Appointment> appointmentList = appointmentService.getAppointmentsBetweenDates(startDate, endDate);
            StringBuilder jsonResponse = new StringBuilder();
            for (Appointment a : appointmentList )
            {
                String json = objectMapper.writeValueAsString(AppointmentDTO.fromEntity(a));
                jsonResponse.append(",").append(json);
            }
            // remove the first comma
            jsonResponse.deleteCharAt(0);
            return new ResponseEntity<>(jsonResponse.toString(), HttpStatus.OK);
        }
        catch (AppointmentNotFoundException e)
        {
            return new ResponseEntity<>("appointment not found for date range", HttpStatus.NOT_FOUND);
        }
        catch (ParseException e)
        {
            return new ResponseEntity<>("date time format error, please use yyyy-MM-dd hh:mm:ss ", HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/appointments/{appointmentId}")
    public ResponseEntity<String> deleteAppointmentById(@PathVariable long appointmentId)
    {
        try
        {
            appointmentService.deleteAppointment(appointmentId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (AppointmentNotFoundException e)
        {
            return new ResponseEntity<>("appointment not found", HttpStatus.NOT_FOUND);
        }
    }
}
