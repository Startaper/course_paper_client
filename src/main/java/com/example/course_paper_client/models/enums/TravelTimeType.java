package com.example.course_paper_client.models.enums;

import com.example.course_paper_client.exceptions.ApiResponseException;

public enum TravelTimeType {

    ANY("Любой"),
    LESS_THAN_HOUR("Меньше часа"),
    FROM_HOUR_TO_ONE_AND_HALF("От часу до полутора часов");

    private final String name;

    TravelTimeType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getTravelTimeTypeByName(String name) throws ApiResponseException {
        switch (name) {
            case "Любой" -> {
                return ANY.toString();
            }
            case "Меньше часа" -> {
                return LESS_THAN_HOUR.toString();
            }
            case "От часу до полутора часов" -> {
                return FROM_HOUR_TO_ONE_AND_HALF.toString();
            }
            default -> throw new ApiResponseException("TravelTimeType не найден");
        }
    }
}
