package com.example.artclient.contoller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String designation;
    private String name;
    private int quantity;
    private int level;
    private int versionDate;
    private List<AssemblyUnitDto> assembliesUnitsDto;
    private String errorMessage;
}
