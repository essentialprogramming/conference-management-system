package com.output;

import com.model.Qualifier;
import lombok.*;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationJSON {

    private int id;
    private Qualifier qualifier;
    private String reviewer;
    private String recommendation;
    private int paperId;
}
