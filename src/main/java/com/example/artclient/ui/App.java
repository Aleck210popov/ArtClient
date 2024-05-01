package com.example.artclient.ui;


import com.example.artclient.service.ProductService;
import com.example.artclient.ui.graphical.StartGraphicalInterface;


public class App {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        StartGraphicalInterface graphicalInterface = new StartGraphicalInterface(productService);
    }
}
