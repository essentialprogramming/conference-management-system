package com.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum Qualifier {

    STRONG_ACCEPT("STRONG_ACCEPT"),
    ACCEPT("ACCEPT"),
    WEAK_ACCEPT("WEAK_ACCEPT"),
    BORDERLINE_PAPER("BORDERLINE_PAPER"),
    WEAK_REJECT("WEAK_REJECT"),
    REJECT("REJECT"),
    STRONG_REJECT("STRONG_REJECT");

    @JsonProperty("value")
    private final String value;

    Qualifier(String value) {
        this.value = value;
    }

    @JsonCreator
    public static Qualifier fromJson(@JsonProperty("qualifier") String qualifier) {
        return valueOf(qualifier);
    }
}
