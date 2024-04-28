package com.example.artclient.service;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.domain.Product;
import com.example.artclient.mapper.ProductMapper;

public class ProductService {
    public void sendPostRequest (Product product) {
        ProductController.sendPostRequest(ProductMapper.toProductDto(product));
    }
    public String[][] sendGetRequestForm (String designation, int versionDate) {
        return ProductController.sendGetRequestForm(designation, versionDate);
    }
}
