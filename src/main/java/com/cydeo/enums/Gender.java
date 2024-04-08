package com.cydeo.enums;

import lombok.Getter;


public enum Gender {

    MALE("Male"), FEMALE("Female");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
