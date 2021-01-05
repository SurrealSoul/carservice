package org.drew.carcenter.data.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.drew.carcenter.data.models.Car;

@Data
public class CarDTO
{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("make")
    private String make;
    @JsonProperty("model")
    private String model;
    @JsonProperty("year")
    private String year;
    @JsonProperty("user_id")
    private Long userId;

    public static CarDTO fromEntity(Car car)
    {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setMake(car.getMake());
        dto.setModel(car.getModel());
        dto.setUserId(car.getUser().getId());
        dto.setYear(car.getYear());

        return dto;
    }
}
