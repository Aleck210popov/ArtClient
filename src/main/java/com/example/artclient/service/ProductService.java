package com.example.artclient.service;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.contoller.dto.ProductDto;
import com.example.artclient.domain.Product;
import com.example.artclient.exception.ProductNotFoundOnServerException;
import com.example.artclient.mapper.ProductMapper;

import java.util.*;

public class ProductService {
    public void sendPostRequest (Product product) {
        ProductController.sendPostRequest(ProductMapper.toProductDto(product));
    }

    public Product sendGetRequestProduct (String designation, int versionDate) {
        ProductDto productDto = ProductController.sendGetRequestProduct(designation, versionDate);
        if (productDto == null) {
            throw new ProductNotFoundOnServerException("Продукт не найден на сервере");
        }
        return ProductMapper.toProductEntity(productDto);
    }

    public void sendDeleteRequestProduct (Product product) {
        ProductController.deleteProduct(product.getId());
    }


}
