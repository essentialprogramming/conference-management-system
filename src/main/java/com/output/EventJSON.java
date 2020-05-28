package com.output;


import com.model.LocationInput;
import com.model.ProgramInput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventJSON {

    private int id;
    private String name;
    private ProgramJSON program;
    private LocationJSON location;
    private List<String> programCommittee;
    private List<String> participants;
    private List<String> speakers;
}
