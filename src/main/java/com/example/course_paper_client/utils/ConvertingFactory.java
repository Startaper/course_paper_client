package com.example.course_paper_client.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Вспомогательный класс
 */
public final class ConvertingFactory {

    /**
     * Метод возвращает строку с json, прочитанный из получаемого файла
     *
     * @param file String url-адрес файла
     * @return String
     * @throws IOException если при чтении файла возникли ошибки
     */
    public static String readJsonFile(String file) throws IOException {
        StringBuilder result = new StringBuilder();

        BufferedReader br = new BufferedReader(new FileReader(file));

        while (true) {
            String line = br.readLine();
            if (line != null && !line.isBlank()) {
                result.append(line);
            } else {
                break;
            }
        }

        br.close();

        return result.toString();
    }

    /**
     * Метод возвращает получаемый url-адрес с заполнением request параметрами из filters
     *
     * @param url     StringBuilder начальное значение url-запроса
     * @param filters Map<String, String> фильтры
     * @return String
     */
    public static String getUrl(StringBuilder url, Map<String, String> filters) {
        if (!filters.isEmpty()) {
            url.append("?");
        } else {
            return url.toString();
        }

        if (filters.containsKey("status")) {
            url.append("status=").append(filters.get("status")).append("&");
        }
        if (filters.containsKey("areaName")) {
            url.append("areaName=").append(filters.get("areaName")).append("&");
        }
        if (filters.containsKey("gender")) {
            url.append("gender=").append(filters.get("gender")).append("&");
        }
        if (filters.containsKey("travelTimeType")) {
            url.append("travelTimeType=").append(filters.get("travelTimeType")).append("&");
        }
        if (filters.containsKey("educationLevel")) {
            url.append("educationLevel=").append(filters.get("educationLevel")).append("&");
        }
        if (filters.containsKey("businessTripReadinessType")) {
            url.append("businessTripReadinessType=").append(filters.get("businessTripReadinessType")).append("&");
        }
        if (filters.containsKey("ageStart")) {
            url.append("ageStart=").append(filters.get("ageStart")).append("&");
        }
        if (filters.containsKey("ageEnd")) {
            url.append("ageEnd=").append(filters.get("ageEnd")).append("&");
        }
        if (filters.containsKey("salaryStart")) {
            url.append("salaryStart=").append(filters.get("salaryStart")).append("&");
        }
        if (filters.containsKey("salaryEnd")) {
            url.append("salaryEnd=").append(filters.get("salaryEnd")).append("&");
        }

        return url.substring(0, url.length() - 1);
    }

}
