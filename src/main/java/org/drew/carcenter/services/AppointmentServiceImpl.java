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
import org.drew.carcenter.utils.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarRepository carRepository;

    @Override
    public Appointment addAppointment(AppointmentDTO appointmentDTO) throws CarNotFoundException, UserNotFoundException {
        if (userRepository.findUserById(appointmentDTO.getUserId()) != null) {
            if (carRepository.findCarById(appointmentDTO.getCarId()) != null) {
                // If the appointment has a valid user and car then proceed to persist it
                Appointment newAppointment = Appointment.builder()
                        .car(carRepository.findCarById(appointmentDTO.getCarId()))
                        .user(userRepository.findUserById(appointmentDTO.getUserId()))
                        .dateTime(appointmentDTO.getDateTime())
                        .price(appointmentDTO.getPrice())
                        .service(appointmentDTO.getService())
                        .status(getStatusFromString(appointmentDTO.getStatus()))
                        .build();
                return appointmentRepository.save(newAppointment);
            } else {
                throw new CarNotFoundException("car not found");
            }
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    @Override
    public Appointment getAppointmentById(Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findAppointmentById(id);
        if (appointment != null) {
            return appointment;
        } else {
            throw new AppointmentNotFoundException("appointment not found");
        }
    }

    @Override
    public List<Appointment> getUsersAppointments(Long userId) throws UserNotFoundException, AppointmentNotFoundException {
        User user = userRepository.findUserById(userId);

        if (user != null) {
            List<Appointment> appointments = appointmentRepository.getAppointmentsByUser(user);
            if (!appointments.isEmpty()) {
                return appointments;
            } else {
                throw new AppointmentNotFoundException("appointments not found");
            }
        } else {
            throw new UserNotFoundException("user not found");
        }
    }

    @Override
    public void deleteAppointment(Long id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentRepository.findAppointmentById(id);

        if (appointment != null) {
            appointmentRepository.deleteById(id);
        } else {
            throw new AppointmentNotFoundException("appointment not found");
        }
    }

    @Override
    public Appointment updateAppointment(AppointmentDTO appointmentDTO, Long appointmentId) throws AppointmentNotFoundException, CarNotFoundException, UserNotFoundException {
        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);
        if (appointment != null) {
            // since we can change the user and car of an appointment, check if those exist as well
            User user = userRepository.findUserById(appointmentDTO.getUserId());
            if (user != null) {
                Car car = carRepository.findCarById(appointmentDTO.getCarId());
                if (car != null) {
                    // build the new appointment
                    Appointment newAppointment = Appointment.builder()
                            .id(appointmentId)
                            .status(getStatusFromString(appointmentDTO.getStatus()))
                            .price(appointmentDTO.getPrice())
                            .user(user)
                            .car(car)
                            .service(appointmentDTO.getService())
                            .dateTime(appointment.getDateTime())
                            .build();
                    return appointmentRepository.save(newAppointment);
                } else {
                    throw new CarNotFoundException("car not found");
                }
            } else {
                throw new UserNotFoundException("user not found");
            }
        } else {
            throw new AppointmentNotFoundException("appointment not found");
        }
    }

    @Override
    public List<Appointment> getAppointmentsBetweenDates(String start, String end) throws AppointmentNotFoundException, ParseException {
        // convert strings to timestamps
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date startDate = dateFormat.parse(start);
        Date endDate = dateFormat.parse(end);
        Timestamp startTimestamp = new Timestamp(startDate.getTime());
        Timestamp endTimestamp = new Timestamp(endDate.getTime());

        List<Appointment> appointments =
                appointmentRepository.findAppointmentsByDateTimeBetweenOrderByPriceDesc(startTimestamp, endTimestamp);

        if (!appointments.isEmpty()) {
            return appointments;
        } else {
            throw new AppointmentNotFoundException("appointments not found for date range");
        }
    }

    /**
     * Creates a random appointment between 2020-01-01 and 2030-01-01
     */
    @Override
    public Appointment addRandomAppointment() {
        String randomString = UUID.randomUUID().toString();
        Double randomPrice = RandomUtils.randomDouble(1, 1_000_000);

        Timestamp min = Timestamp.valueOf("2020-01-01 00:00:00");
        Timestamp max = Timestamp.valueOf("2030-01-01 00:00:00");
        Timestamp randomDate = RandomUtils.randomTimestamp(min, max);

        User user = User.builder()
                .email(randomString)
                .firstName(randomString)
                .lastName(randomString)
                .username(randomString)
                .phone(randomString)
                .build();
        user = userRepository.save(user);

        Car car = Car.builder()
                .make(randomString)
                .model(randomString)
                .year(randomString)
                .user(user)
                .build();
        car = carRepository.save(car);

        Appointment appointment = Appointment.builder()
                .dateTime(randomDate)
                .status(Appointment.Status.pending)
                .service(randomString)
                .user(user)
                .car(car)
                .price(randomPrice)
                .build();

        return appointmentRepository.save(appointment);
    }

    /**
     * Helper function to take a string and convert it to the status enum
     *
     * @param statusString the status string
     * @return an enum value, defaulting to pending if one could not be found
     */
    private Appointment.Status getStatusFromString(String statusString) {
        Appointment.Status statusValue = Appointment.Status.valueOf(statusString);

        if (statusValue == null) {
            statusValue = Appointment.Status.pending;
        }

        return statusValue;
    }
}
