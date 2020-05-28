package com.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperInput {

    private int id;
    private String title;
    private String content;
    private Qualifier[] qualifiers;
    private List<String> authors;
    private List<String> reviewers;
    private List<String> topics;
    private List<String> keywords;
    private List<String> bidders;

}
