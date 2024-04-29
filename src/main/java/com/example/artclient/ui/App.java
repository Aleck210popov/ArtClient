package com.example.artclient.ui;


import com.example.artclient.domain.Product;
import com.example.artclient.service.ProductService;
import com.example.artclient.ui.graphical.FrameMain;


public class App {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
            FrameMain fr = new FrameMain(productService);
            fr.setVisible(true);

//        ProductService productService = new ProductService();
//        String[][] form = productService.sendGetRequestForm("ИБПШ.622665.005", 2024);
//        System.out.println(Arrays.deepToString(form).replace("], ", "]\n"));

    }
}
