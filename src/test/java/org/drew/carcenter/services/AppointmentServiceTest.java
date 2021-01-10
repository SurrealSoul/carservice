package org.drew.carcenter.services;

import org.drew.carcenter.data.models.Appointment;
import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.drew.carcenter.data.models.dto.AppointmentDTO;
import org.drew.carcenter.data.repos.AppointmentRepository;
import org.drew.carcenter.data.repos.CarRepository;
import org.drew.carcenter.data.repos.UserRepository;
import org.drew.carcenter.exceptions.AppointmentNotFoundException;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
public class AppointmentServiceTest {
    @Autowired
    private AppointmentService appointmentService;
    @MockBean
    private CarRepository carRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AppointmentRepository appointmentRepository;

    @Before
    public void setUp() {
        User drew = new User();
        drew.setId(10L);
        drew.setPhone("5133285317");
        drew.setLastName("davis");
        drew.setFirstName("drew");
        drew.setEmail("andrewcdavis0@gmail.com");
        drew.setUsername("andrewcdavis0");

        Mockito.when(userRepository.findUserById(10L))
                .thenReturn(drew);

        Car car = new Car();
        car.setId(1L);
        car.setYear("1991");
        car.setModel("toyota");
        car.setUser(drew);
        car.setModel("landcruiser");

        Mockito.when(carRepository.findCarById(10L)).thenReturn(car);

        Appointment appointment = new Appointment();
        appointment.setId(1L);
        appointment.setUser(drew);
        appointment.setCar(car);
        appointment.setStatus(Appointment.Status.completed);
        appointment.setService("fix car");
        appointment.setPrice(100D);
        appointment.setDateTime(new Timestamp(System.currentTimeMillis() - 10000));

        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setUser(drew);
        appointment2.setCar(car);
        appointment2.setStatus(Appointment.Status.pending);
        appointment2.setService("fix car");
        appointment2.setPrice(100D);
        appointment2.setDateTime(new Timestamp(System.currentTimeMillis()));

        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment2);

        Mockito.when(appointmentRepository.findAppointmentById(1L)).thenReturn(appointment);
        Mockito.when(appointmentRepository.findAppointmentById(2L)).thenReturn(appointment2);
        Mockito.when(appointmentRepository.getAppointmentsByUser(drew)).thenReturn(appointmentList);
    }

    @Test
    public void whenAddingAppointmentWithoutUser_thenThrowUserNotFound() {
        // given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setUserId(9L);
        appointmentDTO.setCarId(10L);

        // when
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () ->
                        appointmentService.addAppointment(appointmentDTO),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("user not found"));
    }

    @Test
    public void whenAddingAppointmentWithoutCar_thenThrowCarNotFound() {
        // given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setUserId(10L);
        appointmentDTO.setCarId(3L);

        // when
        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () ->
                        appointmentService.addAppointment(appointmentDTO),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("car not found"));
    }

    @Test
    public void whenGettingAppointmentThatDoesNotExist_thenThrowAppointmentNotFound() {
        // when
        AppointmentNotFoundException thrown = assertThrows(AppointmentNotFoundException.class, () ->
                        appointmentService.getAppointmentById(99L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("appointment not found"));
    }

    @Test
    public void whenGettingAppointmentsByUserThatDoesNotExist_thenThrowUserNotFound() {
        // when
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () ->
                        appointmentService.getUsersAppointments(99L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("user not found"));
    }

    @Test
    public void whenDeletingAppointmentThatDoesNotExist_thenThrowAppointmentNotFoundException() {
        // when
        AppointmentNotFoundException thrown = assertThrows(AppointmentNotFoundException.class, () ->
                        appointmentService.deleteAppointment(99L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("appointment not found"));
    }

    @Test
    public void whenUpdatingAppointmentThatDoesNotExist_thenThrowAppointmentNotFoundException() {
        // given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setUserId(10L);
        appointmentDTO.setCarId(3L);

        // when
        AppointmentNotFoundException thrown = assertThrows(AppointmentNotFoundException.class, () ->
                        appointmentService.updateAppointment(appointmentDTO, 99L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("appointment not found"));
    }

    @Test
    public void whenUpdatingAppointmentToUserThatDoesNotExist_thenThrowUserNotFoundException() {
        // given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setUserId(99L);
        appointmentDTO.setCarId(1L);

        // when
        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () ->
                        appointmentService.updateAppointment(appointmentDTO, 1L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("user not found"));
    }

    @Test
    public void whenUpdatingAppointmentToCarThatDoesNotExist_thenThrowCarNotFoundException() {
        // given
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setUserId(10L);
        appointmentDTO.setCarId(99L);

        // when
        CarNotFoundException thrown = assertThrows(CarNotFoundException.class, () ->
                        appointmentService.updateAppointment(appointmentDTO, 1L),
                "Expecting appointmentService to throw an exception");

        // then
        assertTrue(thrown.getMessage().equals("car not found"));
    }

    @TestConfiguration
    static class AppointmentServiceImplTestContextConfiguration {
        @Bean
        public AppointmentService appointmentService() {
            return new AppointmentServiceImpl();
        }
    }
}
