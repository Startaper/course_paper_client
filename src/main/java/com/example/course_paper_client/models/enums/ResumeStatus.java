package com.example.course_paper_client.models.enums;

import com.example.course_paper_client.exceptions.ApiResponseException;

public enum ResumeStatus {

    NEW("Новый"),
    DOES_NOT_MEET_THE_REQUIREMENTS("Не соответствует требованиям"),
    AT_WORK("В работе"),
    OFFERED_A_JOB("Предложена вакансия"),
    REFUSED("Отказался");

    private final String name;

    ResumeStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String getStatusByName(String name) throws ApiResponseException {
        switch (name) {
            case "Новый" -> {
                return NEW.toString();
            }
            case "В работе" -> {
                return AT_WORK.toString();
            }
            case "Не соответствует требованиям" -> {
                return DOES_NOT_MEET_THE_REQUIREMENTS.toString();
            }
            case "Предложена вакансия" -> {
                return OFFERED_A_JOB.toString();
            }
            case "Отказался" -> {
                return REFUSED.toString();
            }
            default -> throw new ApiResponseException("ResumeStatus не найден");
        }
    }

}
