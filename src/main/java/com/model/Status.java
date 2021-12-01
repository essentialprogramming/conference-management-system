package com.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Status {
    ACCEPT("ACCEPT"),
    REJECT("REJECT");

    @JsonProperty("value")
    private final String value;

    Status(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Status fromString(String status) {
        return valueOf(status);
    }
}
