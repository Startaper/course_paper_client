package com.example.course_paper_client.models.enums;

import com.example.course_paper_client.exceptions.ApiResponseException;

public enum Gender {
    MALE("Мужской"),
    FEMALE("Женский");

    private final String name;

    Gender(String gender) {
        this.name = gender;
    }

    public String getName() {
        return name;
    }

    public static String getGenderByName(String name) throws ApiResponseException {
        switch (name) {
            case "Мужской" -> {
                return MALE.toString();
            }
            case "Женский" -> {
                return FEMALE.toString();
            }
            default -> throw new ApiResponseException("Gender не найден");
        }
    }
}
