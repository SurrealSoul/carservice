package org.drew.carcenter.api;

import lombok.extern.slf4j.Slf4j;
import org.drew.carcenter.data.models.Appointment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0")
@Slf4j
public class AppointmentController
{
    @PostMapping("/api/v1.0/appointments")
    public Appointment createAppointment()
    {
        return new Appointment();
    }
}
