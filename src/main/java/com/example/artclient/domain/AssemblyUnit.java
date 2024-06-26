package com.example.artclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyUnit {
    private Long id;
    private String designation;
    private String name;
    private int quantity;
    private int level;
    private int versionDate;
    private List<Part> parts;
}