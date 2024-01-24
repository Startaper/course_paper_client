package com.example.course_paper_client.models.enums;

import com.example.course_paper_client.exceptions.ApiResponseException;

public enum BusinessTripReadinessType {
    READY("Готов к командировкам"),
    SOMETIMES("Готов к редким командировкам"),
    NEVER("Не готов к командировкам");

    private final String name;

    BusinessTripReadinessType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getBusinessTripReadinessTypeByName(String name) throws ApiResponseException {
        switch (name) {
            case "Готов к командировкам" -> {
                return READY.toString();
            }
            case "Готов к редким командировкам" -> {
                return SOMETIMES.toString();
            }
            case "Не готов к командировкам" -> {
                return NEVER.toString();
            }
            default -> throw new ApiResponseException("BusinessTripReadinessType не найден");
        }
    }
}
