package org.drew.carcenter.services;

import org.drew.carcenter.data.models.Appointment;
import org.drew.carcenter.data.models.dto.AppointmentDTO;
import org.drew.carcenter.exceptions.AppointmentNotFoundException;
import org.drew.carcenter.exceptions.CarNotFoundException;
import org.drew.carcenter.exceptions.UserNotFoundException;

import java.text.ParseException;
import java.util.List;

public interface AppointmentService {
    Appointment addAppointment(AppointmentDTO appointmentDTO) throws CarNotFoundException, UserNotFoundException;

    Appointment getAppointmentById(Long id) throws AppointmentNotFoundException;

    List<Appointment> getUsersAppointments(Long userId) throws UserNotFoundException, AppointmentNotFoundException;

    void deleteAppointment(Long id) throws AppointmentNotFoundException;

    Appointment updateAppointment(AppointmentDTO appointmentDTO, Long appointmentId) throws AppointmentNotFoundException, UserNotFoundException, CarNotFoundException;

    List<Appointment> getAppointmentsBetweenDates(String startDateTime, String endDateTime) throws AppointmentNotFoundException, ParseException;

    Appointment addRandomAppointment();
}
