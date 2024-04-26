package com.example.artclient;

import com.example.artclient.domain.ProductDto;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {
    private static final String BASE_URL = "http://localhost:8080/api/";
    private static final String ENDPOINT = "products";

    public static void main(String[] args) {
        // Создаем объекты, представляющие JSON для сборочной единицы и детали
        ProductDto product = new ProductDto("ИБПШ.528624.001", "Электропила", 1, 1, null);


        // Отправляем POST запросы для добавления сборочной единицы и детали на сервер
        sendPostRequest(product);
    }

    private static void sendPostRequest(Object data) {
        // Преобразуем объект в строку JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(data);

        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT))
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

