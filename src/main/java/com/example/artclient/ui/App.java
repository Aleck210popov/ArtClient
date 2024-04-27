package com.example.artclient.ui;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.contoller.dto.ProductDto;
import com.example.artclient.domain.Product;
import com.example.artclient.service.ProductService;

public class App {
    public static void main(String[] args) {

        Product product = new Product(null,"ИБПШ.526824.001", "Электропила", 1, 1, 1980, null);
        ProductService productService = new ProductService();
        productService.sendPostRequest(product);

        // Отправляем POST запросы для добавления сборочной единицы и детали на сервер

    }
}
