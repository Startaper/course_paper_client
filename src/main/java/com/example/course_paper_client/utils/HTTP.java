package com.example.course_paper_client.utils;

import com.example.course_paper_client.MainApp;
import com.example.course_paper_client.exceptions.ApiResponseException;
import com.example.course_paper_client.exceptions.NoConnectionException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс-утилита для отправки REST запросов и получения ответов
 */
public class HTTP {

    private static final DataSingleton data = DataSingleton.getInstance();

    /**
     * Отправляет GET-запроса по указанному url-адресу и возвращает ответ
     *
     * @param urlString String url-адрес на который отправляется запрос.
     * @param token     String токен-ключ для доступа к API
     * @return String ответ сервера на запрос
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при парсинге ответа от сервера возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     */
    public static String GetRequest(String urlString, String token) throws NoConnectionException, JSONException, ApiResponseException {
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", "Bearer " + token);

            int code = conn.getResponseCode();
            if (code == 500) {
                data.setTextErrorByAuth("Истекло время вашего сеанса. Просьба авторизоваться по новой.");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("auth-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainApp.changeScene(scene);
                throw new ApiResponseException("Не валидный токен!");
            }

            switch (code / 100) {
                case 2 -> {
                    StringBuilder sb = new StringBuilder();
                    InputStream is = new BufferedInputStream(conn.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String inputLine;
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    return sb.toString();
                }
                case 4 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        JSONObject jsonObject = new JSONObject(response.toString());
                        throw new ApiResponseException(jsonObject.optString("message"));
                    }
                }
                case 5 -> throw new ApiResponseException("Не валидный токен!");
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }


        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * Отправляет POST-запроса и возвращает ответ
     *
     * @param link       String url-адрес на который отправляется запрос.
     * @param jsonObject JSONObject объект, который необходимо отправить на сервер
     * @return String ответ сервера на запрос
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при парсинге ответа от сервера возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     */
    public static String Post(String link, JSONObject jsonObject)
            throws NoConnectionException, ApiResponseException, JSONException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);

            int code = httpURLConnection.getResponseCode();

            switch (code / 100) {
                case 2 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
                    }
                }
                case 4 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        JSONObject jsonObjectResponse = new JSONObject(response.toString());
                        throw new ApiResponseException(jsonObjectResponse.optString("message"));
                    }
                }
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения или сервер не доступен!");
        }
    }

    /**
     * Отправляет POST-запроса и возвращает ответ
     *
     * @param link      String url-адрес на который отправляется запрос.
     * @param jsonArray JSONArray объект, который необходимо отправить на сервер
     * @param token     String токен-ключ для доступа к API
     * @return String ответ сервера на запрос
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при парсинге ответа от сервера возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     */
    public static String Post(String link, JSONArray jsonArray, String token)
            throws NoConnectionException, JSONException, ApiResponseException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            if (jsonArray != null && jsonArray.length() != 0) {
                httpURLConnection.setDoOutput(true);
                try (OutputStream os = httpURLConnection.getOutputStream()) {
                    byte[] input = jsonArray.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int code = httpURLConnection.getResponseCode();
            if (code == 500) {
                data.setTextErrorByAuth("Истекло время вашего сеанса. Просьба авторизоваться по новой.");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("auth-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainApp.changeScene(scene);
                throw new ApiResponseException("Не валидный токен!");
            }

            switch (code / 100) {
                case 2 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
                    }
                }
                case 4 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        JSONObject jsonObject = new JSONObject(response.toString());
                        throw new ApiResponseException(jsonObject.optString("message"));
                    }
                }
                case 5 -> throw new ApiResponseException("Не валидный токен!");
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения или сервер не доступен!");
        }
    }

    /**
     * Отправляет POST-запроса и возвращает ответ
     *
     * @param link       String url-адрес на который отправляется запрос.
     * @param jsonObject JSONObject объект, который необходимо отправить на сервер
     * @param token      String токен-ключ для доступа к API
     * @return String ответ сервера на запрос
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     * @throws JSONException         если при парсинге ответа от сервера возникли ошибки
     */
    public static String Post(String link, JSONObject jsonObject, String token)
            throws NoConnectionException, JSONException, ApiResponseException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");

            if (jsonObject != null && jsonObject.length() != 0) {
                httpURLConnection.setDoOutput(true);
                try (OutputStream os = httpURLConnection.getOutputStream()) {
                    byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int code = httpURLConnection.getResponseCode();
            if (code == 500) {
                data.setTextErrorByAuth("Истекло время вашего сеанса. Просьба авторизоваться по новой.");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("auth-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainApp.changeScene(scene);
                throw new ApiResponseException("Не валидный токен!");
            }

            switch (code / 100) {
                case 2 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        return response.toString();
                    }
                }
                case 4 -> {
                    try (BufferedReader br = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getErrorStream(), StandardCharsets.UTF_8))) {
                        StringBuilder response = new StringBuilder();
                        String responseLine;
                        while ((responseLine = br.readLine()) != null) {
                            response.append(responseLine.trim());
                        }
                        JSONObject jsonObjectResponse = new JSONObject(response.toString());
                        throw new ApiResponseException(jsonObjectResponse.optString("message"));
                    }
                }
                case 5 -> throw new ApiResponseException("Не валидный токен!");
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения или сервер не доступен!");
        }
    }

    /**
     * Отправляет PUT-запроса и возвращает ответ
     *
     * @param urlString String url-адрес на который отправляется запрос.
     * @param token     String токен-ключ для доступа к API
     * @return String ответ сервера на запрос
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     */
    public static String PutRequest(String urlString, JSONObject jsonObject, String token) throws NoConnectionException, ApiResponseException, JSONException {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Content-Type", "application/json; utf-8");
            http.setRequestProperty("Authorization", "Bearer " + token);
            http.setRequestMethod("PUT");

            if (jsonObject != null && jsonObject.length() != 0) {
                http.setDoOutput(true);
                try (OutputStream os = http.getOutputStream()) {
                    byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int code = http.getResponseCode();
            if (code == 500) {
                data.setTextErrorByAuth("Истекло время вашего сеанса. Просьба авторизоваться по новой.");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("auth-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainApp.changeScene(scene);
                throw new ApiResponseException("Не валидный токен!");
            }

            switch (code / 100) {
                case 2 -> {
                    StringBuilder sb = new StringBuilder();
                    InputStream is = new BufferedInputStream(http.getInputStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    return sb.toString();
                }
                case 4 -> {
                    StringBuilder sb = new StringBuilder();
                    InputStream is = new BufferedInputStream(http.getErrorStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    JSONObject jsonObjectResponse = new JSONObject(sb.toString());
                    throw new ApiResponseException(jsonObjectResponse.optString("message"));
                }
                case 5 -> throw new ApiResponseException("Не валидный токен!");
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения или сервер не доступен!");
        }
    }

    /**
     * Отправляет DELETE-запроса и возвращает ответ
     *
     * @param urlString String url-адрес на который отправляется запрос.
     * @param token     String токен-ключ для доступа к API
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws JSONException         если при десериализации возвращаемого ответа возникли ошибки
     * @throws ApiResponseException  возвращается в том случае, если отправлен невалидный токен
     *                               или возникли ошибки на сервере при обработке запроса
     */
    public static void DeleteRequest(String urlString, String token) throws NoConnectionException, JSONException, ApiResponseException {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            conn.setRequestProperty("Authorization", "Bearer " + token);

            http.setRequestMethod("DELETE");

            int code = http.getResponseCode();
            if (code == 500) {
                data.setTextErrorByAuth("Истекло время вашего сеанса. Просьба авторизоваться по новой.");
                FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("auth-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load());
                MainApp.changeScene(scene);
                throw new ApiResponseException("Не валидный токен!");
            }

            switch (code / 100) {
                case 2 -> {
                }
                case 4 -> {
                    StringBuilder sb = new StringBuilder();
                    InputStream is = new BufferedInputStream(http.getErrorStream());
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    String inputLine = "";
                    while ((inputLine = br.readLine()) != null) {
                        sb.append(inputLine);
                    }
                    JSONObject jsonObject = new JSONObject(sb.toString());
                    throw new ApiResponseException(jsonObject.optString("message"));
                }
                case 5 -> throw new ApiResponseException("Не валидный токен!");
                default -> throw new NoConnectionException("Нет соединения или сервер не доступен!");
            }

        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения или сервер не доступен!");
        }
    }

}