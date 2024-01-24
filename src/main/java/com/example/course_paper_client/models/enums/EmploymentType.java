package com.example.course_paper_client.models.enums;

public enum EmploymentType {

    FULL("Полная занятость"),
    PART("Частичная занятость"),
    PROJECT("Проектная работа"),
    VOLUNTEER("Волонтерство"),
    PROBATION("Стажировка");

    private final String name;

    EmploymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
