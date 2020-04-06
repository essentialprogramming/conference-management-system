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
public class Event {

    private int id;
    private String name;
    private List<String> programCommittee;
    private List<String> participants;
    private List<String> speakers;
    private int program;

}