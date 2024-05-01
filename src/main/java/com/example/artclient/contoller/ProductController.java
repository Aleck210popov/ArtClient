package com.example.artclient.contoller;

import com.example.artclient.contoller.dto.ProductDto;
import com.google.gson.Gson;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.example.artclient.constant.Constant.*;

public class ProductController {
    public ProductController() {
    }

    public ProductDto sendPostRequestProduct(ProductDto productDto) {
        // Преобразуем объект в строку JSON
        Gson gson = new Gson();
        String jsonData = gson.toJson(productDto);

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

            // Проверяем статус-код ответа
            if (response.statusCode() == 200) {
                // Возвращаем объект Gson, который пришел с сервера
                return gson.fromJson(response.body(), ProductDto.class);
            } else {
                // Если получен неправильный статус-код, выводим его в консоль
                System.err.println("Error status code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            // Если произошла ошибка при отправке запроса, выводим её в консоль
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
    public ProductDto sendGetRequestProduct(String designation, int versionDate) {
        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT  + DESIGNATION + "/" + designation + VERSION + "/" + versionDate))
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
                return gson.fromJson(response.body(), ProductDto.class);
            } else {
                System.err.println("Error status code: " + response.statusCode());
                ProductDto errorResponse = new ProductDto();
                errorResponse.setErrorMessage("Объект не найден на сервере");
                return errorResponse;
            }
        } catch (Exception e) {
            ProductDto errorResponse = new ProductDto();
            errorResponse.setErrorMessage("Сервер не отвечает");
            System.err.println("Error: " + e.getMessage());
            return errorResponse;
        }
    }
    public void sendDeleteRequestProduct(long id) {
        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT + ID + "/" + id))
                .DELETE()
                .build();

        // Отправляем запрос и получаем ответ
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Проверяем код ответа
            if (response.statusCode() == 200) {

                System.out.println("Product successfully deleted.");
            } else {
                System.err.println("Error: " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
    public ProductDto sendPutRequestProduct(long id, ProductDto productDto) {

        Gson gson = new Gson();
        String jsonData = gson.toJson(productDto);
        // Создаем HTTP клиент
        HttpClient httpClient = HttpClient.newHttpClient();

        // Создаем запрос к серверу
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + ENDPOINT + ID + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        // Отправляем запрос и получаем ответ
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Проверяем код ответа
            if (response.statusCode() == 200) {
                System.out.println("Product successfully update.");
                return gson.fromJson(response.body(), ProductDto.class);


            } else {
                System.err.println("Error status code: " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }
}
