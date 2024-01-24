package com.example.course_paper_client.models.enums;

import com.example.course_paper_client.exceptions.ApiResponseException;

public enum EducationLevel {

    SECONDARY("Среднее"),
    SPECIAL_SECONDARY("Среднее специальное"),
    UNFINISHED_HIGHER("Неоконченное высшее"),
    HIGHER("Высшее"),
    BACHELOR("Бакалавр"),
    MASTER("Магистр"),
    CANDIDATE("Кандидат наук"),
    DOCTOR("Доктор наук");

    private final String name;

    EducationLevel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getEducationLevelByName(String name) throws ApiResponseException {
        switch (name) {
            case "Среднее" -> {
                return SECONDARY.toString();
            }
            case "Среднее специальное" -> {
                return SPECIAL_SECONDARY.toString();
            }
            case "Неоконченное высшее" -> {
                return UNFINISHED_HIGHER.toString();
            }
            case "Высшее" -> {
                return HIGHER.toString();
            }
            case "Бакалавр" -> {
                return BACHELOR.toString();
            }
            case "Магистр" -> {
                return MASTER.toString();
            }
            case "Кандидат наук" -> {
                return CANDIDATE.toString();
            }
            case "Доктор наук" -> {
                return DOCTOR.toString();
            }
            default -> throw new ApiResponseException("EducationLevel не найден");
        }
    }
}
