package org.drew.carcenter.data.models.dto;

import lombok.Data;

@Data
public class CarDTO
{
    private String make;
    private String model;
    private String year;
    private Long userId;
}
