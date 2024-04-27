package com.example.artclient.mapper;

import com.example.artclient.contoller.dto.AssemblyUnitDto;
import com.example.artclient.contoller.dto.PartDto;
import com.example.artclient.contoller.dto.ProductDto;
import com.example.artclient.domain.AssemblyUnit;
import com.example.artclient.domain.Part;
import com.example.artclient.domain.Product;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ProductMapper {
    public Product toProductEntity(ProductDto productDto) {

        Product product =  Product.builder()
                .id(productDto.getId())
                .designation(productDto.getDesignation())
                .name(productDto.getName())
                .quantity(productDto.getQuantity())
                .level(productDto.getLevel())
                .versionDate(productDto.getVersionDate())
                .build();
        if (productDto.getAssembliesUnitsDto() != null) {
            product.setAssembliesUnits(toAssemblyUnit(productDto.getAssembliesUnitsDto(), product));
        }
        return product;
    }

    private List<AssemblyUnit> toAssemblyUnit(List<AssemblyUnitDto> assembliesUnitsDto, Product product) {
        List<AssemblyUnit> assemblyUnits = new ArrayList<>();
        for (AssemblyUnitDto assemblyUnitDto : assembliesUnitsDto) {
            AssemblyUnit assemblyUnit = AssemblyUnit.builder()
                    .id(assemblyUnitDto.getId())
                    .designation(assemblyUnitDto.getDesignation())
                    .name(assemblyUnitDto.getName())
                    .quantity(assemblyUnitDto.getQuantity())
                    .level(assemblyUnitDto.getLevel())
                    .versionDate(assemblyUnitDto.getVersionDate())
                    .build();

            if (assemblyUnitDto.getPartsDto() != null)
                assemblyUnit.setParts(toParts(assemblyUnitDto.getPartsDto(), assemblyUnit));

            assemblyUnits.add(assemblyUnit);
        }
        return assemblyUnits;
    }

    private List<Part> toParts(List<PartDto> partsDto, AssemblyUnit assemblyUnit) {
        List<Part> parts = new ArrayList<>();
        for (PartDto partDto : partsDto) {
            Part part = Part.builder()
                    .id(partDto.getId())
                    .designation(partDto.getDesignation())
                    .name(partDto.getName())
                    .quantity(partDto.getQuantity())
                    .level(partDto.getLevel())
                    .versionDate(partDto.getVersionDate())
                    .build();

            if (partDto.getId() != null) part.setId(partDto.getId());

            parts.add(part);
        }
        return parts;
    }

    public ProductDto toProductDto(Product product) {

        ProductDto productDto = ProductDto.builder()
                .id(product.getId())
                .designation(product.getDesignation())
                .name(product.getName())
                .quantity(product.getQuantity())
                .level(product.getLevel())
                .versionDate(product.getVersionDate())
                .build();


        if (product.getId() != null) productDto.setId(product.getId());
        if (product.getAssembliesUnits() != null)
            productDto.setAssembliesUnitsDto(toAssemblyUnitDto(product.getAssembliesUnits()));

        return productDto;
    }


    private List<AssemblyUnitDto> toAssemblyUnitDto(List<AssemblyUnit> assembliesUnits) {
        List<AssemblyUnitDto> assembliesUnitsDto = new ArrayList<>();
        for (AssemblyUnit assemblyUnit : assembliesUnits) {
            AssemblyUnitDto assemblyUnitDto = AssemblyUnitDto.builder()
                    .designation(assemblyUnit.getDesignation())
                    .name(assemblyUnit.getName())
                    .quantity(assemblyUnit.getQuantity())
                    .level(assemblyUnit.getLevel())
                    .versionDate(assemblyUnit.getVersionDate())
                    .build();

            if (assemblyUnit.getId() != null) assemblyUnitDto.setId(assemblyUnit.getId());
            if (assemblyUnit.getParts() != null)
                assemblyUnitDto.setPartsDto(toPartsDto(assemblyUnit.getParts()));

            assembliesUnitsDto.add(assemblyUnitDto);

        }
        return assembliesUnitsDto;
    }

    private List<PartDto> toPartsDto(List<Part> parts) {
        List<PartDto> partsDto = new ArrayList<>();
        for (Part part : parts) {
            PartDto partDto = PartDto.builder()
                    .designation(part.getDesignation())
                    .name(part.getName())
                    .quantity(part.getQuantity())
                    .level(part.getLevel())
                    .versionDate(part.getVersionDate())
                    .build();

            if (part.getId() != null) partDto.setId(part.getId());

            partsDto.add(partDto);
        }
        return partsDto;
    }
}
