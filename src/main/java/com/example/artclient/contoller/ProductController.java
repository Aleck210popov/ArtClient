package com.example.artclient.contoller;

import com.example.artclient.contoller.dto.ProductDto;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.artclient.constant.Constant.*;

public class ProductController {
    public static void sendPostRequest(ProductDto productDto) {
        // Преобразуем объект в строку JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(productDto);

        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT))
                .header("Accept", "application/json")
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
    public static String[][] sendGetRequestForm(String designation, int versionDate) {
        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT + FORM + "/designation/" + designation + "/version/" + versionDate))
                .header("Content-Type", "application/json")
                .GET()
                .build();

        // Отправляем запрос и получаем ответ
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Проверяем код ответа
            if (response.statusCode() == 200) {
                // Преобразуем JSON в массив строк
                Gson gson = new Gson();
                return gson.fromJson(response.body(), String[][].class);
            } else {
                System.err.println("Error: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
