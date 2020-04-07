package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Role {

    PARTICIPANT("PARTICIPANT"),
    SPEAKER("SPEAKER"),
    PROGRAM_COMMITTEE("PROGRAM_COMMITTEE");

    @JsonProperty("value")
    private String value;

    Role(String value){
        this.value = value;
    }

    @JsonCreator
    public static Role fromJson(@JsonProperty("name") String name) {
        return valueOf(name);
    }
}
