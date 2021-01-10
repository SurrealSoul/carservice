package org.drew.carcenter.data.repos;

import org.drew.carcenter.data.models.Appointment;
import org.drew.carcenter.data.models.Car;
import org.drew.carcenter.data.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@DataJpaTest
public class AppointmentRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    public void whenFindByUser_ThenReturnAppointments() {
        // given
        User user = new User();
        user.setEmail("user@user.com");
        user.setUsername("user");
        user.setFirstName("user");
        user.setLastName("user");
        user.setPhone("5133285317");
        entityManager.persistAndFlush(user);

        User user2 = new User();
        user2.setEmail("user2@user.com");
        user2.setUsername("user2");
        user2.setFirstName("user2");
        user2.setLastName("user2");
        user2.setPhone("5133285317");
        entityManager.persistAndFlush(user2);

        // they will all use the same car
        Car car = new Car();
        car.setMake("make");
        car.setModel("model");
        car.setYear("1991");
        car.setUser(user);
        entityManager.persistAndFlush(car);

        Appointment appointment = new Appointment();
        appointment.setCar(car);
        appointment.setDateTime(new Timestamp(System.currentTimeMillis()));
        appointment.setPrice(100D);
        appointment.setService("test");
        appointment.setUser(user);
        appointment.setStatus(Appointment.Status.pending);
        entityManager.persistAndFlush(appointment);

        Appointment appointment2 = new Appointment();
        appointment2.setCar(car);
        appointment2.setDateTime(new Timestamp(System.currentTimeMillis()));
        appointment2.setPrice(100D);
        appointment2.setService("test");
        appointment2.setUser(user);
        appointment2.setStatus(Appointment.Status.completed);
        entityManager.persistAndFlush(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setCar(car);
        appointment3.setDateTime(new Timestamp(System.currentTimeMillis()));
        appointment3.setPrice(100D);
        appointment3.setService("test");
        appointment3.setUser(user2);
        appointment3.setStatus(Appointment.Status.approved);
        entityManager.persistAndFlush(appointment3);


        // when
        List<Appointment> userAppointments = appointmentRepository.getAppointmentsByUser(user);
        List<Appointment> user2Appointments = appointmentRepository.getAppointmentsByUser(user2);

        // then
        assertThat(userAppointments.size()).isEqualTo(2);
        assertThat(user2Appointments.size()).isEqualTo(1);
        assertThat(userAppointments.get(0).getStatus()).isEqualTo(Appointment.Status.pending);
        assertThat(userAppointments.get(1).getStatus()).isEqualTo(Appointment.Status.completed);
        assertThat(user2Appointments.get(0).getStatus()).isEqualTo(Appointment.Status.approved);
    }

    @Test
    public void whenFindAppointmentsInDateRange_thenReturnAppointmentsOrderByPrice() {
        // given
        // they will all use the same user
        User user = new User();
        user.setEmail("user@user.com");
        user.setUsername("user");
        user.setFirstName("user");
        user.setLastName("user");
        user.setPhone("5133285317");
        entityManager.persistAndFlush(user);

        // they will all use the same car
        Car car = new Car();
        car.setMake("make");
        car.setModel("model");
        car.setYear("1991");
        car.setUser(user);
        entityManager.persistAndFlush(car);

        Appointment appointment = new Appointment();
        appointment.setCar(car);
        appointment.setDateTime(new Timestamp(System.currentTimeMillis() - 1000));
        appointment.setPrice(100D);
        appointment.setService("test");
        appointment.setUser(user);
        appointment.setStatus(Appointment.Status.pending);
        entityManager.persistAndFlush(appointment);

        Appointment appointment2 = new Appointment();
        appointment2.setCar(car);
        appointment2.setDateTime(new Timestamp(System.currentTimeMillis() + 100));
        appointment2.setPrice(9D);
        appointment2.setService("test");
        appointment2.setUser(user);
        appointment2.setStatus(Appointment.Status.completed);
        entityManager.persistAndFlush(appointment2);

        Appointment appointment3 = new Appointment();
        appointment3.setCar(car);
        appointment3.setDateTime(new Timestamp(System.currentTimeMillis() + 1000));
        appointment3.setPrice(999D);
        appointment3.setService("test");
        appointment3.setUser(user);
        appointment3.setStatus(Appointment.Status.approved);
        entityManager.persistAndFlush(appointment3);

        // out of range
        Appointment appointment4 = new Appointment();
        appointment4.setCar(car);
        appointment4.setDateTime(new Timestamp(System.currentTimeMillis() + 9999));
        appointment4.setPrice(888D);
        appointment4.setService("test");
        appointment4.setUser(user);
        appointment4.setStatus(Appointment.Status.approved);
        entityManager.persistAndFlush(appointment4);


        // when
        List<Appointment> userAppointments = appointmentRepository.
                findAppointmentsByDateTimeBetweenOrderByPriceDesc(new Timestamp(System.currentTimeMillis() - 2000),
                        new Timestamp(System.currentTimeMillis() + 2000));

        // then
        assertThat(userAppointments.size()).isEqualTo(3);
        assertThat(userAppointments.get(0)).isEqualTo(appointment3);
        assertThat(userAppointments.get(2)).isEqualTo(appointment2);
    }
}
