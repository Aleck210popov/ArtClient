package com.example.artclient.service;

import com.example.artclient.contoller.ProductController;
import com.example.artclient.contoller.dto.ProductDto;
import com.example.artclient.domain.Product;
import com.example.artclient.exception.ProductNotFoundOnServerException;
import com.example.artclient.exception.ProductWasNotSavedException;
import com.example.artclient.mapper.ProductMapper;

public class ProductService {
    private final ProductController productController;

    public ProductService() {
        this.productController = new ProductController();
    }

    public Product sendPostRequestProduct (Product product) {
        ProductDto productDto = productController.sendPostRequestProduct(ProductMapper.toProductDto(product));
        if (productDto == null) {
            throw new ProductWasNotSavedException("Не удалось сохранить продукт");
        }
        return ProductMapper.toProductEntity(productDto);
    }
    public Product sendGetRequestProduct (String designation, int versionDate) {
        try {
            ProductDto productDto = productController.sendGetRequestProduct(designation, versionDate);
            if (productDto.getErrorMessage()!=null) {
                throw new ProductNotFoundOnServerException(productDto.getErrorMessage());
            }
            return ProductMapper.toProductEntity(productDto);
        } catch (ProductNotFoundOnServerException e) {
            return null;
        }
    }

    public void sendDeleteRequestProduct (Product product) {
        productController.sendDeleteRequestProduct(product.getId());
    }
    public Product sendPutRequestProduct (long id, Product product) {

        ProductDto productDto = productController.sendPutRequestProduct(id,
                ProductMapper.toProductDto(product));
        if (productDto == null) {
            throw new ProductNotFoundOnServerException("Продукт не найден на сервере");
        }
        return ProductMapper.toProductEntity(productDto);
    }



}
