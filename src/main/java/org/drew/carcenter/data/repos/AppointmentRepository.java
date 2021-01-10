package org.drew.carcenter.data.repos;

import org.drew.carcenter.data.models.Appointment;
import org.drew.carcenter.data.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    Appointment save(Appointment appointment);

    void deleteById(Long id);

    Appointment findAppointmentById(Long id);

    List<Appointment> getAppointmentsByUser(User user);

    List<Appointment> findAppointmentsByDateTimeBetweenOrderByPriceDesc(Timestamp startDate, Timestamp endDate);
}
