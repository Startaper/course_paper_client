package com.example.course_paper_client.utils;

import com.example.course_paper_client.models.*;
import com.example.course_paper_client.models.enums.ContactType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс
 */
public final class MainUtil {

    /**
     * Метод возвращает строку с json, прочитанный из получаемого файла
     *
     * @param file String url-адрес файла
     * @return String
     * @throws IOException если при чтении файла возникли ошибки
     */
    public static String readJsonFile(File file) throws IOException {
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

    public static Contact getContactByContactType(List<Contact> contacts, ContactType type) {
        contacts = contacts.stream().filter(e -> e.getType() == type).toList();
        if (contacts.size() == 0) {
            return null;
        }
        return contacts.get(0);
    }

    public static String convertListToString(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(", ");
        }

        return sb.substring(0, sb.length() - 2);
    }

    /**
     * Метод конвертирует получаемый jsonArray в список соц. сетей
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Site> convertJsonArrayToSiteList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Site> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Site(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список контактов
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Contact> convertJsonArrayToContactList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Contact> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Contact(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список языков
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Language> convertJsonArrayToLanguageList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Language> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Language(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список образований
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Education> convertJsonArrayToEducationList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Education> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Education(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список, содержащий информацию об опыте работы соискателя.
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Experience> convertJsonArrayToExperienceList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Experience> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Experience(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список специализаций
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Specialization> convertJsonArrayToSpecializationList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Specialization> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Specialization(jsonArray.getJSONObject(i)));
        }
        return result;
    }

    /**
     * Метод конвертирует получаемый jsonArray в список рекомендаций
     *
     * @param jsonArray JSONArray
     * @return List<>
     * @throws JSONException если при парсинге json возникли ошибки
     */
    public static List<Recommendation> convertJsonArrayToRecommendationList(JSONArray jsonArray) throws JSONException {
        if (jsonArray == null || jsonArray.length() == 0) {
            return null;
        }
        List<Recommendation> result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            result.add(new Recommendation(jsonArray.getJSONObject(i)));
        }
        return result;
    }

}
