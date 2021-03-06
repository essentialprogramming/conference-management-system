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

    private String title;
    private String description;
    private String fileName;
    private List<String> authors;
    private List<String> topics;
    private List<String> keywords;

}
