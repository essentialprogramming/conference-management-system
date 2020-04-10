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
    private String email;
    private int paperId;

    public Recommendation(String text, String email, int paperId) {
        this.text = text;
        this.email = email;
        this.paperId = paperId;
    }
}
