package com.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionJSON {

    private int id;
    private String name;
    private int eventId;
    private String supervisorEmail;
    private List<String> attendees;
}
