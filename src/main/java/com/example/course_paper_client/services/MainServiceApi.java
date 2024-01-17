package com.example.course_paper_client.services;

import com.example.course_paper_client.exceptions.NoConnectionException;
import com.example.course_paper_client.utils.ConvertingFactory;
import com.example.course_paper_client.utils.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

/**
 * Класс-утилита для генерации url-запросов, а также для авторизации и регистрации.
 */
public class MainServiceApi {

    private static final String AUTH_PATTERN = "/login";
    private static final String REGISTRATION_PATTERN = "/registration";
    private static final String USER_PATTERN = "/api/resumes/";
    private static final String ADMIN_PATTERN = "/admin/api/resumes/";

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
    public static String login(String username, String password) throws NoConnectionException, IOException, JSONException {
        JSONObject result;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", username);
        jsonObject.put("password", password);

        result = new JSONObject(HTTP.Post(localhost() + AUTH_PATTERN, jsonObject));

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
            throws NoConnectionException, IOException, JSONException {
        JSONObject result;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("lastName", lastName);
        jsonObject.put("firstName", firstName);
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("passwordRepeat", passwordRepeat);

        result = new JSONObject(HTTP.Post(localhost() + REGISTRATION_PATTERN, jsonObject));

        return result.getString("token");
    }

    /**
     * Метод отправляет запрос на добавление списка резюме, прочитанного из json-файла
     *
     * @param fileUrl String ссылка на файл с json данными
     * @param token   String ключ-доступа к API
     * @return JSONArray
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException           если при соединении или чтении файла возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static JSONArray addResumes(String fileUrl, String token) throws JSONException, IOException, NoConnectionException {
        JSONArray jsonArray = new JSONArray(ConvertingFactory.readJsonFile(fileUrl));

        return new JSONArray(HTTP.Post(localhost() + ADMIN_PATTERN, jsonArray, token));
    }

    /**
     * Метод отправляет запрос на получение всех объекты, хранящиеся в БД, по заданным фильтрам.
     *
     * @param token   String ключ-доступа к API
     * @param filters Map<String, String> фильтры
     * @return JSONArray
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static JSONArray getAll(String token, Map<String, String> filters) throws NoConnectionException, JSONException {
        StringBuilder url = new StringBuilder();
        url.append(localhost()).append(USER_PATTERN);

        String urlStr = ConvertingFactory.getUrl(url, filters);

        return new JSONArray(HTTP.GetRequest(urlStr, token));
    }

    /**
     * Метод отправляет запрос на получение определенного объекта по-заданному id.
     *
     * @param token String ключ-доступа к API
     * @param id    String id запрашиваемого объекта
     * @return JSONObject
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     */
    public static JSONObject get(String token, String id) throws NoConnectionException, JSONException {
        return new JSONObject(HTTP.GetRequest(localhost() + USER_PATTERN + id, token));
    }

    /**
     * Метод отправляет запрос на удаление объект по-заданному id
     *
     * @param token String ключ-доступа к API
     * @param id String id запрашиваемого объекта
     * @return Boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean delete(String token, String id) throws NoConnectionException {
        return HTTP.DeleteRequest(localhost() + ADMIN_PATTERN + id, token);
    }

    /**
     * Метод отправляет запрос на удаление из БД все объекты.
     *
     * @param token String ключ-доступа к API
     * @param id String id запрашиваемого объекта
     * @return Boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean deleteAll(String token, String id) throws NoConnectionException {
        return HTTP.DeleteRequest(localhost() + ADMIN_PATTERN, token);
    }

    /**
     * Метод отправляет запрос на обновление статуса объекта по-заданному id
     *
     * @param token String ключ-доступа к API
     * @param id String id запрашиваемого объекта
     * @param newStatus String новый статус
     * @return JSONObject
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException если при десериализации возвращаемого ответа возникли ошибки
     */
    public static JSONObject update(String token, String id, String newStatus) throws NoConnectionException, JSONException {
        return new JSONObject(HTTP.PatchRequest(localhost() + USER_PATTERN + id + "?newStatus=" + newStatus, token));
    }

}
