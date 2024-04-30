package com.example.artclient.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

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

    public String[][] getForm() {

        List<String[]> form = new ArrayList<>();

        Map<String, Integer> quantitiesMap = new HashMap<>();

        putQuantitiesMap(quantitiesMap);
        processMap(quantitiesMap, form);

        return form.toArray(new String[0][]);
    }

    private void putQuantitiesMap(Map<String, Integer> quantitiesMap) {
        quantitiesMap.put(this.getDesignation(), this.getQuantity());

        for (AssemblyUnit assemblyUnit : this.getAssembliesUnits()) {

            if (quantitiesMap.containsKey(assemblyUnit.getDesignation())) {
                Integer fds = quantitiesMap.get(assemblyUnit.getDesignation());
                fds+=assemblyUnit.getQuantity() * this.getQuantity();
                quantitiesMap.put(assemblyUnit.getDesignation(), fds);
            } else {
                quantitiesMap.put(assemblyUnit.getDesignation(), assemblyUnit.getQuantity()*this.getQuantity());
            }
            for (Part part : assemblyUnit.getParts()) {
                if (quantitiesMap.containsKey(part.getDesignation())) {
                    Integer rrfds = quantitiesMap.get(part.getDesignation());
                    rrfds+=part.getQuantity()*assemblyUnit.getQuantity()*this.getQuantity();
                    quantitiesMap.put(part.getDesignation(), rrfds);
                } else {
                    quantitiesMap.put(part.getDesignation(), part.getQuantity()*assemblyUnit.getQuantity()*this.getQuantity());
                }
            }
        }
    }

    private void processMap(Map<String, Integer> quantitiesMap, List<String[]> form) {
        form.add(new String[]{this.getDesignation(),
                this.getDesignation(),
                this.getDesignation(),
                String.valueOf(this.getLevel()),
                String.valueOf(this.getQuantity()),
                String.valueOf(this.getQuantity()),
                String.valueOf(this.getQuantity())});


        for (AssemblyUnit assemblyUnit : this.getAssembliesUnits()) {
            form.add(new String[]{this.getDesignation(),
                    this.getDesignation(),
                    assemblyUnit.getDesignation(),
                    String.valueOf(assemblyUnit.getLevel()),
                    String.valueOf(quantitiesMap.get(assemblyUnit.getDesignation())),
                    String.valueOf(assemblyUnit.getQuantity() * this.getQuantity()),
                    String.valueOf(assemblyUnit.getQuantity())});
            for (Part part : assemblyUnit.getParts()) {
                form.add(new String[]{this.getDesignation(),
                        assemblyUnit.getDesignation(),
                        part.getDesignation(),
                        String.valueOf(part.getLevel()),
                        String.valueOf(quantitiesMap.get(part.getDesignation())),
                        String.valueOf(part.getQuantity() * assemblyUnit.getQuantity()),
                        String.valueOf(part.getQuantity())});
            }
        }
    }
}
