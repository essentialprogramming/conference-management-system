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
public class EventInput {

    private String name;
    private ProgramInput program;
    private LocationInput location;

    public ProgramInput getProgram()
    {
        return program;
    }

    public LocationInput getLocation()
    {
        return location;
    }

    public String getName()
    {
        return name;
    }
}
