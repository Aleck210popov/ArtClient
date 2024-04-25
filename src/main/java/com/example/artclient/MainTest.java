package com.example.artclient;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
public class MainTest {
    public static void main(String[] args) {
        // JSON строка с данными о продукте
        String productJson = "{\"designation\":\"ИБПШ.526824.001\",\"name\":\"Электродвигатель\",\"quantity\":1,\"level\":1,\"assemblyUnits\":[{\"designation\":\"ЖБИК.684222.002\",\"name\":\"Статор\",\"quantity\":2,\"level\":2,\"parts\":[{\"designation\":\"ЖБИК.301134.001\",\"name\":\"Корпус статора\",\"quantity\":1,\"level\":3},{\"designation\":\"ЖБИК.757481.001\",\"name\":\"Гильза прямоугольная\",\"quantity\":45,\"level\":3}]},{\"designation\":\"ЖБИК.684261.003\",\"name\":\"Ротор\",\"quantity\":1,\"level\":2,\"parts\":[{\"designation\":\"ЖБИК.301176.001\",\"name\":\"Крышка ротора\",\"quantity\":1,\"level\":3},{\"designation\":\"ЖБИК.714662.001\",\"name\":\"Втулка\",\"quantity\":10,\"level\":3},{\"designation\":\"ЖБИК.758121.007\",\"name\":\"Болт\",\"quantity\":16,\"level\":3}]},{\"designation\":\"ЖБИК.301131.001\",\"name\":\"Корпус\",\"quantity\":1,\"level\":2}],\"parts\":[{\"designation\":\"ЖБИК.758121.007\",\"name\":\"Болт\",\"quantity\":20,\"level\":2},{\"designation\":\"ЖБИК.757481.001\",\"name\":\"Гильза прямоугольная\",\"quantity\":20,\"level\":2}]}";

        // Создание объекта клиента HTTP
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Создание HTTP POST запроса
            HttpPost httpPost = new HttpPost("http://localhost:8080/api/products");

            // Установка заголовков
            httpPost.setHeader("Content-Type", "application/json");

            // Установка тела запроса (JSON данные о продукте)
            StringEntity stringEntity = new StringEntity(productJson, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);

            // Отправка запроса и получение ответа
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                // Обработка ответа
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    System.out.println("Response status: " + response.getStatusLine());
                    // Вывод тела ответа, если нужно
                    // String responseBody = EntityUtils.toString(responseEntity);
                    // System.out.println("Response body: " + responseBody);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
