package com.example.artclient;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.domain.ProductDto;

public class App {
    public static void main(String[] args) {
        // Создаем объекты, представляющие JSON для сборочной единицы и детали
        ProductDto product = new ProductDto("ИБПШ.528624.001", "Электропила", 1, 1, null);


        // Отправляем POST запросы для добавления сборочной единицы и детали на сервер
        ProductController.sendPostRequest(product);
    }
}
