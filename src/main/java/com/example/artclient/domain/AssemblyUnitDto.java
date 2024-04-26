package com.example.artclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class AssemblyUnitDto {
    private String designation;
    private String name;
    private int quantity;
    private int level;
    private List<PartDto> partsDto;
}
