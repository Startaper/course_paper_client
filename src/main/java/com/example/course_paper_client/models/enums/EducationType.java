package com.example.course_paper_client.models.enums;

public enum EducationType {

    ADDITIONAL("Курсы повышения квалификации"),
    ATTESTATION("Тесты или экзамены"),
    ELEMENTARY("Среднее образование"),
    PRIMARY("Образование выше среднего");

    private final String name;

    EducationType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
