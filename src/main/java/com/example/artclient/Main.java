package com.example.artclient;

import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    public static void main(String[] args) {
        // Создаем объект, представляющий JSON
        Product product = new Product("ИБПШ.528624.001", "Электропила", 1, 1);

        // Преобразуем объект в строку JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(product);

        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/products")) // Укажите адрес вашего сервера и точку входа API для добавления продукта
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        // Отправляем запрос и получаем ответ
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Выводим ответ в консоль
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

