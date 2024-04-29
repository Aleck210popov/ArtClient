package com.example.artclient.ui;



import com.example.artclient.service.ProductService;
import com.example.artclient.ui.graphical.FrameMain;


public class App {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        FrameMain fr = new FrameMain(productService);
        fr.setVisible(true);
    }
}
