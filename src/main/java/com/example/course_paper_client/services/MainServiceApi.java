package com.example.course_paper_client.services;

import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.models.Resume;
import com.example.course_paper_client.models.User;
import com.example.course_paper_client.utils.MainUtil;
import com.example.course_paper_client.utils.DataSingleton;
import com.example.course_paper_client.utils.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Класс-утилита для генерации url-запросов, а также для авторизации и регистрации.
 */
public class MainServiceApi {

    private static final String AUTH_PATTERN = "/login";
    private static final String REGISTRATION_PATTERN = "/registration";
    private static final String USER_PATTERN = "/api/resumes/";
    private static final String ADMIN_RESUMES_PATTERN = "/admin/api/resumes/";
    private static final String ADMIN_USERS_PATTERN = "/admin/users/";
    private static final DataSingleton data = DataSingleton.getInstance();

    /**
     * @return String возвращает основной url-адрес сервера
     */
    private static String localhost() {
        return "http://localhost:8878";
    }

    /**
     * Метод отправляет запрос на авторизацию на сервере
     *
     * @param username String
     * @param password String
     * @return String возвращает token - ключ-доступа к API
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException           если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static String login(String username, String password) throws NoConnectionException, ApiResponseException, IOException, JSONException {
        JSONObject result;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        result = new JSONObject(HTTP.Post(localhost() + AUTH_PATTERN, jsonObject));

        data.setAdmin(result.optBoolean("admin"));

        return result.getString("token");
    }

    /**
     * Метод отправляет запрос на регистрацию на сервере
     *
     * @param lastName       String Фамилия
     * @param firstName      String Имя
     * @param email          String Эл. почта
     * @param password       String Пароль
     * @param passwordRepeat String Повторение пароля
     * @return String возвращает token - ключ-доступа к API
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException           если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static String registration(String lastName, String firstName, String email, String password, String passwordRepeat)
            throws NoConnectionException, IOException, ApiResponseException, JSONException {
        JSONObject result;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", lastName);
        jsonObject.put("firstName", firstName);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("passwordRepeat", passwordRepeat);

        result = new JSONObject(HTTP.Post(localhost() + REGISTRATION_PATTERN, jsonObject));

        data.setAdmin(result.optBoolean("admin"));

        return result.getString("token");
    }

    /**
     * Метод отправляет запрос на добавление списка резюме, прочитанного из json-файла
     *
     * @param file  String ссылка на файл с json данными
     * @param token String ключ-доступа к API
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException           если при соединении или чтении файла возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static void addResumes(File file, String token) throws JSONException, IOException, NoConnectionException, ApiResponseException {
        JSONArray jsonArrayRequest = new JSONArray(MainUtil.readJsonFile(file));
        HTTP.Post(localhost() + ADMIN_RESUMES_PATTERN, jsonArrayRequest, token);
    }

    /**
     * Метод отправляет запрос на получение всех объекты, хранящиеся в БД, по заданным фильтрам.
     *
     * @param token   String ключ-доступа к API
     * @param filters Map<String, String> фильтры
     * @return Resume
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static List<Resume> getAllResumes(String token, Map<String, String> filters) throws NoConnectionException, JSONException, ApiResponseException, IOException {
        List<Resume> resumes = new ArrayList<>();
        String url = localhost() + USER_PATTERN;
        JSONObject jsonObject;
        if (filters == null || filters.isEmpty()) {
            jsonObject = new JSONObject(HTTP.Post(url, new JSONObject(), token));
        } else {
            jsonObject = new JSONObject(HTTP.Post(url, new JSONObject(filters), token));
        }

        JSONArray jsonArray;
        if (jsonObject.has("resumes")) {
            jsonArray = jsonObject.getJSONArray("resumes");
            for (int i = 0; i < jsonArray.length(); i++) {
                resumes.add(new Resume(jsonArray.getJSONObject(i)));
            }
        }

        return resumes;
    }

    /**
     * Метод отправляет запрос на получение определенного объекта по-заданному id.
     *
     * @param token String ключ-доступа к API
     * @param id    String id запрашиваемого объекта
     * @return Resume
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static Resume get(String token, String id) throws NoConnectionException, ApiResponseException, JSONException {
        JSONObject jsonObject = new JSONObject(HTTP.GetRequest(localhost() + USER_PATTERN + id, token));
        return new Resume(jsonObject);
    }

    /**
     * Метод отправляет запрос на удаление объект по-заданному id
     *
     * @param token String ключ-доступа к API
     * @param id    String id запрашиваемого объекта
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static void deleteResume(String token, String id) throws NoConnectionException, ApiResponseException, JSONException {
        HTTP.DeleteRequest(localhost() + ADMIN_RESUMES_PATTERN + id, token);
    }

    /**
     * Метод отправляет запрос на удаление из БД все объекты.
     *
     * @param token String ключ-доступа к API
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static void deleteAllResumes(String token) throws NoConnectionException, ApiResponseException, JSONException {
        HTTP.DeleteRequest(localhost() + ADMIN_RESUMES_PATTERN, token);
    }

    /**
     * Метод отправляет запрос на обновление статуса объекта по-заданному id
     *
     * @param token     String ключ-доступа к API
     * @param id        String id запрашиваемого объекта
     * @param newStatus String новый статус
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static void updateResume(String token, String id, String newStatus) throws NoConnectionException, ApiResponseException, JSONException {
        HTTP.PutRequest(localhost() + USER_PATTERN + id + "?newStatus=" + newStatus, null, token);
    }

    public static void addUser(String token, JSONObject userJson) throws NoConnectionException, IOException, ApiResponseException, JSONException {
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(userJson);
        HTTP.Post(localhost() + ADMIN_USERS_PATTERN, jsonArray, token);
    }

    public static void updateUser(String token, Long id, User user) throws NoConnectionException, ApiResponseException, JSONException {
        JSONObject jsonObject = user.toJson();

        HTTP.PutRequest(localhost() + ADMIN_USERS_PATTERN + id, jsonObject, token);
    }

    public static void deleteUser(String token, Long id) throws NoConnectionException, ApiResponseException, JSONException {
        HTTP.DeleteRequest(localhost() + ADMIN_USERS_PATTERN + id, token);
    }

    public static List<User> getAllUsers(String token) throws NoConnectionException, JSONException, ApiResponseException, IOException {
        List<User> users = new ArrayList<>();
        String url = localhost() + ADMIN_USERS_PATTERN;
        JSONObject jsonObject = new JSONObject(HTTP.GetRequest(url, token));

        JSONArray jsonArray;
        if (jsonObject.has("users")) {
            jsonArray = jsonObject.getJSONArray("users");
            for (int i = 0; i < jsonArray.length(); i++) {
                users.add(new User(jsonArray.getJSONObject(i)));
            }
        }

        return users;
    }
}
