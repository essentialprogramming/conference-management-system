package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {

    private int id;
    private String title;
    private String content;
    private Qualifier[] qualifiers;

}
