package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Role {
    AUTHOR("AUTHOR"),
    PC_MEMBER("PC_MEMBER"),
    CONFERENCE_CHAIR("CONFERENCE_CHAIR");

    @JsonProperty("value")
    private String value;

    Role(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Role fromJson(@JsonProperty("role") String role) {
        return valueOf(role);
    }
}
