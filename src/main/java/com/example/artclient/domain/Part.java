package com.example.artclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Part {
    private String designation;
    private String name;
    private int quantity;
    private int level;
}
