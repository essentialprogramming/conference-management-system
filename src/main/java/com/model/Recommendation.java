package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recommendation {

    private int id;
    private String text;

    public Recommendation(String text) {
        this.text = text;
    }
}
