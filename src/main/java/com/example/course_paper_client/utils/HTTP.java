package com.example.course_paper_client.utils;
import com.example.course_paper_client.exceptions.NoConnectionException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * Класс-утилита для отправки REST запросов и принятия ответов
 */
public class HTTP {

    /**
     * Отправляет GET-запроса по указанному url-адресу и возвращает ответ
     * @param urlString String
     * @param token String
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static String GetRequest(String urlString, String token) throws NoConnectionException {
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();

            conn.setRequestProperty("Authorization", "Bearer " + token);

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * Отправляет POST-запроса и возвращает ответ
     * @param link String
     * @param jsonObject JSONObject
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException если при соединении возникли ошибки
     */
    public static String Post(String link, JSONObject jsonObject)
            throws NoConnectionException, IOException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = jsonObject.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (ConnectException e) {
            throw new NoConnectionException("Нет соединения");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Отправляет POST-запроса и возвращает ответ
     * @param link String
     * @param jsonArray JSONArray
     * @param token String
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     * @throws IOException если при соединении возникли ошибки
     */
    public static String Post(String link, JSONArray jsonArray, String token)
            throws NoConnectionException, IOException {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Authorization", "Bearer " + token);
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            try (OutputStream os = httpURLConnection.getOutputStream()) {
                byte[] input = jsonArray.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } catch (ConnectException e) {
            throw new NoConnectionException("Нет соединения");
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Отправляет PATCH-запроса и возвращает ответ
     * @param urlString String
     * @param token String
     * @return String
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static String PatchRequest(String urlString, String token) throws NoConnectionException{
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            http.setRequestProperty("Authorization", "Bearer " + token);
            http.setRequestMethod("PATCH");
            http.setDoOutput(false);

            StringBuilder sb = new StringBuilder();
            InputStream is = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoConnectionException("Нет соединения");
        }
    }

    /**
     * Отправляет DELETE-запроса и возвращает ответ
     * @param urlString String
     * @param token String
     * @return boolean
     * @throws NoConnectionException если при соединении возникли ошибки
     */
    public static boolean DeleteRequest(String urlString, String token) throws  NoConnectionException{
        try {

            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            conn.setRequestProperty("Authorization", "Bearer " + token);

            http.setRequestMethod("DELETE");
            http.setDoOutput(true);
            http.connect();

            int code = http.getResponseCode();
            return code == 200;
        } catch (IOException e) {
            throw new NoConnectionException("Нет соединения");
        }
    }

}