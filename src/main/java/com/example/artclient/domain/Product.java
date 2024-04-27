package com.example.artclient.domain;

import com.example.artclient.contoller.dto.AssemblyUnitDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Product {
    private Long id;
    private String designation;
    private String name;
    private int quantity;
    private int level;
    private int versionDate;
    private List<AssemblyUnit> assembliesUnits;
}
