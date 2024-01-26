package com.example.course_paper_client.exceptions;

/**
 * Класс исключения, когда возвращается ошибка о невалидном токене
 * или возникли ошибки на сервере при обработке запроса
 */
public class ApiResponseException extends Exception {
    /**
     * Инициализатор класса, использующий наследуемый метод
     *
     * @param message сообщение, которое необходимо отобразить в ошибке
     */
    public ApiResponseException(String message) {
        super(message);
    }
}
