package com.example.artclient.service;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.contoller.dto.ProductDto;
import com.example.artclient.domain.Product;
import com.example.artclient.exception.ProductNotFoundOnServerException;
import com.example.artclient.exception.ProductWasNotSavedException;
import com.example.artclient.mapper.ProductMapper;

public class ProductService {
    public Product sendPostRequestProduct (Product product) {
        ProductDto productDto = ProductController.sendPostRequestProduct(ProductMapper.toProductDto(product));
        if (productDto == null) {
            throw new ProductNotFoundOnServerException("Продукт не найден на сервере");
        }
        return ProductMapper.toProductEntity(productDto);
    }
    public Product sendGetRequestProduct (String designation, int versionDate) {
        ProductDto productDto = ProductController.sendGetRequestProduct(designation, versionDate);
        if (productDto == null) {
            throw new ProductWasNotSavedException("Продукт не сохранился");
        }
        return ProductMapper.toProductEntity(productDto);
    }

    public void sendDeleteRequestProduct (Product product) {
        ProductController.sendDeleteRequestProduct(product.getId());
    }
    public Product sendPutRequestProduct (long id, Product product) {

        ProductDto productDto = ProductController.sendPutRequestProduct(id,
                ProductMapper.toProductDto(product));
        if (productDto == null) {
            throw new ProductNotFoundOnServerException("Продукт не найден на сервере");
        }
        return ProductMapper.toProductEntity(productDto);
    }



}
