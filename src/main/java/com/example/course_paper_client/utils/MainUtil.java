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
import java.util.*;

/**
 * Вспомогательный класс
 */
public final class MainUtil {

    /**
     * Метод возвращает строку с json, прочитанный из получаемого файла
     *
     * @param file String url-адрес файла
     * @return String прочитанный файл
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

    /**
     * Метод возвращает объект типа Contact в зависемости от указанных параметров
     *
     * @param contacts список всех контактов, среди которых необходимо найти соответствующий условию параметра 2
     * @param type тип параметра, который требуется вернуть
     * @return Contact
     */
    public static Contact getContactByContactType(List<Contact> contacts, ContactType type) {
        contacts = contacts.stream().filter(e -> e.getType() == type).toList();
        if (contacts.size() == 0) {
            return null;
        }
        return contacts.get(0);
    }

    /**
     * Метод преобразует список строк в кастомизированную строку
     *
     * @param list Список строк, который необходимо преобразовать
     * @return String преобразованная строка
     */
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
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Site>
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
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Contact>
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
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Language>
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
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Education>
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
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Experience>
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
     * Метод конвертирует получаемый jsonArray в список рекомендаций
     *
     * @param jsonArray JSONArray массив json-объектов для преобразования в список
     * @return List<Recommendation>
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

    /**
     * Метод фильтрует список резюме по следующим полям - фамилия, имя и отчество, а также должности
     *
     * @param resumes список резюме
     * @param text искомое поле
     * @return List<Resume> отфильтрованный список
     */
    public static List<Resume> searchByText(List<Resume> resumes, String text) {
        Map<String, List<String>> listStrBySearch = new HashMap<>();
        for (Resume resume : resumes) {
            listStrBySearch.put(resume.getId(), Arrays.stream(resume.getSearchField().split("[ .,\\-+='/?!`~@#$%^&*()]")).toList());
        }

        return resumes.stream()
                .filter(resume -> {
                    if (text == null || text.isEmpty()) {
                        return false;
                    } else {
                        List<String> strSearchResume = listStrBySearch.get(resume.getId());
                        for (String s : text.split("[ .,\\-+='/?!`~@#$%^&*()]")) {
                            return strSearchResume.contains(s);
                        }
                    }
                    return false;
                })
                .toList();
    }
}
