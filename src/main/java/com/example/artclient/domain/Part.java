package com.example.artclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Part {
    private Long id;
    private String designation;
    private String name;
    private int quantity;
    private int level;
    private int versionDate;
}
