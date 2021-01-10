package org.drew.carcenter.data.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.drew.carcenter.data.models.Appointment;

import java.sql.Timestamp;

@Data
public class AppointmentDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("status")
    private String Status;
    @JsonProperty("date_time")
    private Timestamp dateTime;
    @JsonProperty("car_id")
    private Long carId;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("price")
    private Double price;
    @JsonProperty("service")
    private String service;

    /**
     * Creates a DTO object to return to the user from a persisted entity
     *
     * @param appointment the persisted entity
     * @return a DTO object to return to the user
     */
    public static AppointmentDTO fromEntity(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setCarId(appointment.getCar().getId());
        dto.setDateTime(appointment.getDateTime());
        dto.setPrice(appointment.getPrice());
        dto.setService(appointment.getService());
        dto.setStatus(appointment.getStatus().toString());
        dto.setUserId(appointment.getUser().getId());

        return dto;
    }
}
