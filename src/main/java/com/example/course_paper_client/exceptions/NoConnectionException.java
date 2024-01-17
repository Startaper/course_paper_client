package com.example.course_paper_client.exceptions;

/**
 * Класс исключения, когда отсутствует соединение с сервером
 */
public class NoConnectionException extends  Exception{
    /**
     * Инициализатор класса, использующий наследуемый метод
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public NoConnectionException(String message) {
        super(message);
    }
}